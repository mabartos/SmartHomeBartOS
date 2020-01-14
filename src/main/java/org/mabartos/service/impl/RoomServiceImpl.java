package org.mabartos.service.impl;

import org.mabartos.general.RoomType;
import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.RoomModel;
import org.mabartos.persistence.repository.RoomRepository;
import org.mabartos.service.core.RoomService;

import javax.inject.Inject;
import java.util.List;

public class RoomServiceImpl extends CRUDServiceChildImpl<RoomModel, RoomRepository, HomeModel> implements RoomService {

    @Inject
    RoomServiceImpl(RoomRepository repository) {
        super(repository);
    }

    @Override
    public List<RoomModel> findByType(RoomType type) {
        return getRepository().find("type", type).list();
    }
}
