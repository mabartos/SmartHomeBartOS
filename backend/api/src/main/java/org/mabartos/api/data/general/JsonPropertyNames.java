/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.data.general;

public interface JsonPropertyNames {
    String ID = "id";
    String NAME = "name";

    String TYPE = "type";
    String PIN = "pin";

    String HOME_ID = "homeID";
    String ROOM_ID = "roomID";
    String DEVICE_ID = "deviceID";

    String ACTIVE = "active";
    String ENABLED = "enabled";
    String DISABLED = "disabled";

    // CAPABILITIES
    String CAPABILITIES = "capabilities";

    String TRIGGER_INPUT_CAP_ID="inputCapID";
    String TRIGGER_OUTPUT_CAP_ID="outputCapID";
    String TRIGGER_INPUT_STATE="inputState";
    String TRIGGER_OUTPUT_STATE="outputState";

    String USER = "user";
    String USER_ID = "userID";
    String USERS_COUNT="usersCount";

    String ROLE = "role";
    String OWNER_ID = "ownerID";
    String ISSUER_ID = "issuerID";
    String RECEIVER_ID = "receiverID";

    // MQTT
    String MQTT_CLIENT_ID = "mqttClientID";
    String TOPIC = "topic";
    String BROKER_URL = "brokerURL";


}
