package org.mabartos.services.model;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.RoomService;
import org.mabartos.controller.data.RoomData;
import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.room.RoomModel;
import org.mabartos.persistence.model.user.UserModel;
import org.mabartos.persistence.repository.RoomRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class RoomServiceImpl extends CRUDServiceImpl<RoomModel, RoomRepository, Long> implements RoomService {

    private AppServices services;

    @Inject
    RoomServiceImpl(RoomRepository repository, AppServices services) {
        super(repository);
        this.services = services;
    }

    public void start(@Observes StartupEvent event) {
    }

    @Override
    public Set<RoomModel> findByType(RoomType type) {
        return getRepository().find("type", type).stream().collect(Collectors.toSet());
    }

    @Override
    public int deleteAllFromHome(Long homeID) {
        Query query = entityManager.createNamedQuery("deleteRoomsFromHome");
        query.setParameter("homeID", homeID);
        return query.executeUpdate();
    }

    @Override
    public Set<UserModel> getOwners(Long roomID) {
        RoomModel room = getRepository().findById(roomID);
        if (room != null && room.getOwnersID() != null) {
            Set<UUID> ownersID = room.getOwnersID();
            Set<UserModel> owners = new HashSet<>();
            ownersID.forEach(id -> owners.add(services.users().findByID(id)));
            return owners.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public boolean addOwnerByID(Long roomID, UUID id) {
        RoomModel room = getRepository().findById(roomID);
        return room != null && room.addOwnerID(id);
    }

    @Override
    public boolean removeOwner(Long roomID, UUID id) {
        RoomModel room = getRepository().findById(roomID);
        return room != null && room.removeOwnerID(id);
    }

    @Override
    public boolean isOwner(Long roomID, UUID id) {
        RoomModel room = getRepository().findById(roomID);
        return room != null && room.isOwner(id);
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
