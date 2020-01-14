package org.mabartos.service.core;

import org.mabartos.persistence.model.HomeModel;
import org.mabartos.persistence.model.UserModel;

public interface HomeService extends CRUDServiceChild<HomeModel, UserModel> {

    HomeModel findByName(String name);

    HomeModel findByBrokerURL(String brokerURL);
}
