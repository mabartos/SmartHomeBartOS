package org.mabartos.service.impl;

import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.repository.HomeRepository;
import org.mabartos.service.core.HomeService;

public class HomeServiceImpl extends CRUDServiceImpl<HomeModel, HomeRepository> implements HomeService {

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
