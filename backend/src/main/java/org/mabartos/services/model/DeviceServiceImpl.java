package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.DeviceService;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.repository.DeviceRepository;
import org.mabartos.protocols.mqtt.exceptions.DeviceConflictException;

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
            return updateByID(deviceID, device);
        }
        return null;
    }

    @Override
    public boolean removeDeviceFromRoom(Long roomID, Long deviceID) {
        DeviceModel device = findByID(deviceID);
        RoomModel room = services.rooms().findByID(roomID);
        if (device != null && room != null) {
            device.setRoom(null);
            room.removeChild(device);
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

        return super.deleteByID(id);
    }
}
