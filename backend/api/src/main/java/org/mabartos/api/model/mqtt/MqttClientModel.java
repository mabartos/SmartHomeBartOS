/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.model.mqtt;

import org.mabartos.api.common.IdentifiableName;
import org.mabartos.api.model.home.HomeModel;

public interface MqttClientModel extends IdentifiableName<Long> {

    void setName(String name);

    HomeModel getHome();

    Long getHomeID();

    void setHome(HomeModel home);

    String getTopic();

    void setTopic(String topic);

    String getBrokerURL();

    void setBrokerURL(String brokerURL);

    boolean isBrokerActive();

    void setBrokerActive(boolean state);
}
