/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt;

public interface AbleSendMQTT {
    void clearRetainedMessages(Long id);
}
