/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.persistence.jpa.model.services.device;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.common.CapabilityType;
import org.mabartos.api.data.general.device.AddDeviceToRoomData;
import org.mabartos.api.data.general.device.DeviceData;
import org.mabartos.api.data.general.device.DeviceInfoData;
import org.mabartos.api.model.device.DeviceModel;
import org.mabartos.api.model.home.HomeModel;
import org.mabartos.api.model.room.RoomModel;
import org.mabartos.api.protocol.mqtt.BartMqttClient;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.protocol.mqtt.TopicUtils;
import org.mabartos.api.protocol.mqtt.exceptions.DeviceConflictException;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.device.DeviceService;
import org.mabartos.persistence.jpa.repository.DeviceRepository;
import org.mabartos.services.model.CRUDServiceImpl;
import org.mabartos.services.utils.DataToModelBase;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.Set;
import java.util.stream.Collectors;

@Dependent
public class DeviceServiceImpl extends CRUDServiceImpl<DeviceModel, DeviceEntity, DeviceRepository, Long> implements DeviceService {

    private AppServices services;

    @Inject
    DeviceServiceImpl(DeviceRepository repository, AppServices services) {
        super(repository);
        this.services = services;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public DeviceModel create(DeviceModel entity) {
        if (!isDeviceInHome(entity))
            return super.create(entity);
        else throw new DeviceConflictException();
    }

    @Override
    public Set<DeviceModel> findByType(CapabilityType type) {
        return getRepository().find("type", type).stream().collect(Collectors.toSet());
    }

    @Override
    public DeviceModel addDeviceToRoom(Long roomID, Long deviceID) {
        DeviceModel device = findByID(deviceID);
        RoomModel room = services.rooms().findByID(roomID);
        if (device != null && room != null) {
            device.setRoom(room);
            room.addChild(device);
            sendDeviceInRoomMessage(roomID, deviceID);
            return updateByID(deviceID, device);
        }
        return null;
    }

    @Override
    public int deleteAllFromHome(Long homeID) {
        HomeModel home = services.homes().findByID(homeID);
        if (home != null) {
            home.getUnAssignedDevices().forEach(f -> {
                Query queryCaps = entityManager.createNamedQuery("deleteCapsFromDevice");
                queryCaps.setParameter("deviceID", f.getID());
                queryCaps.executeUpdate();
                sendEraseAllInDeviceHW(f.getID());
                clearRetainedMessages(f.getID());
            });
        }

        Query query = entityManager.createNamedQuery("deleteDevicesFromHome");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }

    @Override
    public DeviceModel updateFromJson(Long ID, String JSON) {
        DeviceModel device = getRepository().findById(ID);
        if (device != null) {
            DeviceInfoData data = DeviceInfoData.fromJson(JSON);
            device = DataToModelBase.toDeviceModel(data, device);

            if (!device.getHomeID().equals(data.getHomeID())) {
                device.setHome(services.homes().findByID(data.getHomeID()));
            }
            if (!device.getRoomID().equals(data.getRoomID())) {
                device.setRoom(services.rooms().findByID(data.getRoomID()));
            }

            return updateByID(ID, device);
        }
        return null;
    }


    @Override
    public boolean removeDeviceFromRoom(Long roomID, Long deviceID) {
        DeviceModel device = findByID(deviceID);
        RoomModel room = services.rooms().findByID(roomID);
        if (device != null && room != null) {
            device.setRoom(null);
            clearRetainedMessages(deviceID);
            room.removeChild(device);
            sendDeviceInRoomMessage(roomID, (long) -1);
            return updateByID(deviceID, device) != null;
        }
        return false;
    }

    @Override
    public DeviceModel fromDataToModel(DeviceData data) {
        if (data != null) {
            DeviceModel model = new DeviceEntity(data.getName(), services.capabilities().fromDataToModel(data.getCapabilities()));
            if (data.getId() != null) {
                model.setID(data.getId());
            }
            return model;
        }
        return null;
    }

    private boolean isDeviceInHome(DeviceModel device) {
        return DeviceEntity.find("name", device.getName()).count() > 0;
    }

    @Override
    public boolean deleteByID(Long id) {
        Query query = entityManager.createNamedQuery("deleteCapsFromDevice");
        query.setParameter("deviceID", id);
        query.executeUpdate();

        sendEraseAllInDeviceHW(id);
        clearRetainedMessages(id);

        return super.deleteByID(id);
    }

    @Override
    public void clearRetainedMessages(Long id) {
        DeviceModel device = findByID(id);
        if (device != null && device.getHome() != null) {
            BartMqttClient client = services.mqttManager().getMqttForHome(device.getHomeID());
            if (client == null)
                return;
            MqttClientManager.clearRetainedMessages(client, TopicUtils.getDeviceTopic(device.getHomeID(), id));

            device.getCapabilities().forEach(cap -> {
                services.capabilities().clearRetainedMessages(cap.getID());
                MqttClientManager.clearRetainedMessages(client, TopicUtils.getCapabilityTopic(cap));
            });

            if (device.getRoom() != null) {
                MqttClientManager.clearRetainedMessages(client, TopicUtils.getDeviceTopicInRoom(device.getHomeID(), device.getRoomID(), id));
            }
        }
    }

    private void sendDeviceInRoomMessage(Long roomID, Long deviceID) {
        DeviceModel device = findByID(deviceID);
        if (device != null && device.getHome() != null && roomID != null) {
            BartMqttClient client = services.mqttManager().getMqttForHome(device.getHomeID());
            if (client != null) {
                client.publish(TopicUtils.getDeviceTopic(device.getHomeID(), deviceID), new AddDeviceToRoomData(roomID, deviceID, true).toJson(), 1, false);
            }
        }
    }

    private void sendEraseAllInDeviceHW(Long deviceID) {
        DeviceModel device = findByID(deviceID);
        if (device != null && device.getHome() != null) {
            BartMqttClient client = services.mqttManager().getMqttForHome(device.getHomeID());
            if (client != null) {
                client.publish(TopicUtils.getEraseAllDeviceHWTopic(device.getHomeID(), deviceID), "{}", 1, false);
            }
        }
    }
}
