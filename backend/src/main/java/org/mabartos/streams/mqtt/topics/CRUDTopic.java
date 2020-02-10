package org.mabartos.streams.mqtt.topics;

public class CRUDTopic implements GeneralTopic {
    private TopicType topicType;
    private Long homeID;
    private CRUDTopicType typeCRUD;
    public static final Integer TOPIC_ITEMS_COUNT = 3;

    private CRUDTopic(CRUDTopicType typeCRUD) {
        this.typeCRUD = typeCRUD;
        topicType = TopicType.CRUD_TOPIC;
    }

    public CRUDTopic(Long homeID, CRUDTopicType typeCRUD) {
        this(typeCRUD);
        this.homeID = homeID;
    }

    public CRUDTopic(String homeID, CRUDTopicType typeCRUD) {
        this(typeCRUD);
        this.homeID = Long.parseLong(homeID);
    }

    @Override
    public TopicType getTopicType() {
        return topicType;
    }

    @Override
    public Integer getTopicItemsCount() {
        return TOPIC_ITEMS_COUNT;
    }

    public Long getHomeID() {
        return homeID;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }

    public CRUDTopicType getTypeCRUD() {
        return typeCRUD;
    }

    public void setType(CRUDTopicType type) {
        this.typeCRUD = type;
    }
}
