package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.DeviceService;
import org.mabartos.api.service.RoomService;
import org.mabartos.controller.data.RoomData;
import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.repository.RoomRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.Set;
import java.util.stream.Collectors;

@Dependent
public class RoomServiceImpl extends CRUDServiceImpl<RoomModel, RoomRepository, Long> implements RoomService {

    private DeviceService deviceService;

    @Inject
    RoomServiceImpl(RoomRepository repository, DeviceService deviceService) {
        super(repository);
        this.deviceService = deviceService;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public Set<RoomModel> findByType(RoomType type) {
        return getRepository().find("type", type).stream().collect(Collectors.toSet());
    }

    @Override
    public RoomModel updateFromJson(Long roomID, String JSON) {
        RoomModel room = getRepository().findById(roomID);
        if (room != null) {
            RoomData data = RoomData.fromJson(JSON);
            room.setName(data.getName());
            return updateByID(roomID, room);
        }
        return null;
    }

    @Override
    public boolean deleteByID(Long id) {
        Query query = entityManager.createNamedQuery("setDeviceRoomToNull");
        query.setParameter("roomID", id);
        query.executeUpdate();
        
        query = entityManager.createNamedQuery("deleteRoomByID");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }
}
