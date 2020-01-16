package org.mabartos.service.impl;

import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;
import org.mabartos.persistence.repository.HomeRepository;
import org.mabartos.service.core.HomeService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class HomeServiceImpl extends CRUDServiceChildImpl<HomeModel, HomeRepository, UserModel> implements HomeService {

    @Inject
    HomeServiceImpl(HomeRepository repository) {
        super(repository);
    }

    @Override
    public HomeModel findByName(String name) {
        return getRepository().find("name", name).firstResult();
    }

    @Override
    public HomeModel findByBrokerURL(String brokerURL) {
        return getRepository().find("brokerURL", brokerURL).firstResult();
    }


}
