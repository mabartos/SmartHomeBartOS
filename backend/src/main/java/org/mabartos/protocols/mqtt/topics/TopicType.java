package org.mabartos.protocols.mqtt.topics;

public enum TopicType {

    CRUD_TOPIC(CRUDTopic.class),
    DEVICE_TOPIC(DeviceTopic.class),
    CAPABILITY_TOPIC(CapabilityTopic.class);

    private Object object;

    TopicType(Object object) {
        this.object = object;
    }

    public Object getTypeClass() {
        return object;
    }
}
