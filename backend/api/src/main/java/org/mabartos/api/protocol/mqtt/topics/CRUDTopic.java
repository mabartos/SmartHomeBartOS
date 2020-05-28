/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.api.protocol.mqtt.topics;

public class CRUDTopic implements GeneralTopic {
    private Long homeID;
    private Long deviceID;
    private CRUDTopicType typeCRUD;

    private CRUDTopic(CRUDTopicType typeCRUD) {
        this.typeCRUD = typeCRUD;
    }

    public CRUDTopic(Long homeID, Long deviceID, CRUDTopicType typeCRUD) {
        this(typeCRUD);
        this.homeID = homeID;
        if (!typeCRUD.equals(CRUDTopicType.CREATE))
            this.deviceID = deviceID;
    }

    public CRUDTopic(String homeID, Long deviceID, CRUDTopicType typeCRUD) {
        this(Long.parseLong(homeID), deviceID, typeCRUD);
    }

    public CRUDTopic(String homeID, String deviceID, CRUDTopicType typeCRUD) {
        this(typeCRUD);
        this.homeID = Long.parseLong(homeID);
        if (!typeCRUD.equals(CRUDTopicType.CREATE))
            this.deviceID = Long.parseLong(deviceID);

    }

    @Override
    public TopicType getTopicType() {
        return TopicType.CRUD_TOPIC;
    }

    public Long getHomeID() {
        return homeID;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public CRUDTopicType getTypeCRUD() {
        return typeCRUD;
    }

    public void setType(CRUDTopicType type) {
        this.typeCRUD = type;
    }
}
