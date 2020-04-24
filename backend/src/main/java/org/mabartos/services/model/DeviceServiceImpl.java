package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.protocol.BartMqttClient;
import org.mabartos.api.protocol.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.DeviceService;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.home.HomeModel;
import org.mabartos.persistence.model.room.RoomModel;
import org.mabartos.persistence.repository.DeviceRepository;
import org.mabartos.protocols.mqtt.data.device.AddDeviceToRoomData;
import org.mabartos.protocols.mqtt.exceptions.DeviceConflictException;
import org.mabartos.protocols.mqtt.utils.TopicUtils;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.Set;
import java.util.stream.Collectors;

@Dependent
public class DeviceServiceImpl extends CRUDServiceImpl<DeviceModel, DeviceRepository, Long> implements DeviceService {

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
            BartMqttClient client = services.mqttManager().getMqttForHome(room.getHomeID());
            if (client != null) {
                client.publish(TopicUtils.getDeviceTopic(room.getHomeID(), deviceID), new AddDeviceToRoomData(roomID, deviceID,true).toJson());
            }
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
                clearRetainedMessages(f.getID());
            });
        }

        Query query = entityManager.createNamedQuery("deleteDevicesFromHome");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }

    @Override
    public boolean removeDeviceFromRoom(Long roomID, Long deviceID) {
        DeviceModel device = findByID(deviceID);
        RoomModel room = services.rooms().findByID(roomID);
        if (device != null && room != null) {
            device.setRoom(null);
            room.removeChild(device);
            clearRetainedMessages(deviceID);
            return updateByID(deviceID, device) != null;
        }
        return false;
    }

    private boolean isDeviceInHome(DeviceModel device) {
        return DeviceModel.find("name", device.getName()).count() > 0;
    }

    @Override
    public boolean deleteByID(Long id) {
        Query query = entityManager.createNamedQuery("deleteCapsFromDevice");
        query.setParameter("deviceID", id);
        query.executeUpdate();

        clearRetainedMessages(id);

        return super.deleteByID(id);
    }

    @Override
    public void clearRetainedMessages(Long id) {
        DeviceModel device = findByID(id);
        if (device != null && device.getHome() != null) {
            BartMqttClient client = services.mqttManager().getMqttForHome(device.getHomeID());
            MqttClientManager.clearRetainedMessages(client, TopicUtils.getDeviceTopic(device.getHomeID(), id));
            device.getCapabilities().forEach(cap -> services.capabilities().clearRetainedMessages(cap.getID()));

            if (device.getRoom() != null) {
                MqttClientManager.clearRetainedMessages(client, TopicUtils.getDeviceTopicInRoom(device.getHomeID(), device.getRoomID(), id));
            }
        }
    }
}
