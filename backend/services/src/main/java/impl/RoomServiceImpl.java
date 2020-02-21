package impl;

import models.HomeModel;
import models.RoomModel;
import repository.RoomRepository;
import service.RoomService;
import utils.RoomType;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Dependent
public class RoomServiceImpl extends CRUDServiceChildImpl<RoomModel, RoomRepository, HomeModel> implements RoomService {

    @Inject
    RoomServiceImpl() {
        super(RoomRepository.class);
    }

    @Override
    public Set<RoomModel> findByType(RoomType type) {
        return getRepository().find("type", type).stream().collect(Collectors.toSet());
    }
}
