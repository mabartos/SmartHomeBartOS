package org.mabartos.service.core;

import org.mabartos.persistence.model.HomeModel;

public interface HomeService extends CRUDService<HomeModel> {

    HomeModel findByName(String name);

    HomeModel findByBrokerURL(String brokerURL);
}
