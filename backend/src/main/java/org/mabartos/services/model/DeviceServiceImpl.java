package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.DeviceService;
import org.mabartos.general.CapabilityType;
import org.mabartos.persistence.model.DeviceModel;
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

    @Inject
    DeviceServiceImpl(DeviceRepository repository) {
        super(repository);
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
