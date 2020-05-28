/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.topics;

public interface GeneralTopic {

    Long getHomeID();

    TopicType getTopicType();
}
