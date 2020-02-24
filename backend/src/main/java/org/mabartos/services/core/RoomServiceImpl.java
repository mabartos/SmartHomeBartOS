package org.mabartos.services.core;

import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.repository.RoomRepository;
import org.mabartos.api.service.RoomService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Dependent
public class RoomServiceImpl extends CRUDServiceChildImpl<RoomModel, RoomRepository, HomeModel> implements RoomService {

    @Inject
    RoomServiceImpl(RoomRepository repository) {
        super(repository);
    }

    @Override
    public Set<RoomModel> findByType(RoomType type) {
        return getRepository().find("type", type).stream().collect(Collectors.toSet());
    }
}
