#include "MessageForwarder.h"

#include "device/Device_deps.h"

extern Device device;

bool MessageForwarder::equalsTopic(const char *receiveTopic) {
    return (strcmp(_topic, receiveTopic) == 0);
}

bool MessageForwarder::equalsTopic(const string &receiveTopic) {
    return (strcmp(_topic, receiveTopic.c_str()) == 0);
}

void MessageForwarder::manageCreate(const JsonObject &obj) {
    if (equalsTopic(device.getHomeTopic())) {
        if (obj.containsKey("code") && obj["code"] != 200) {
            return;
        }

        long msgID = obj["msgID"];

        if (msgID == device.getManageMsgID()) {
            long ID = obj["id"];
            device.setID(ID);

            JsonArray caps = obj["capabilities"];
            for (JsonObject cap : caps) {
                long capID = cap["id"];
                const char *name = cap["name"];
                auto p_cap = device.getCapabilityByName(name);
                if (p_cap != nullptr) {
                    p_cap->setID(capID);
                }
            }
            device.setInitialized(true);
        }
    }
}
void MessageForwarder::manageAddDeviceToRoom(const JsonObject &obj) {
    if (equalsTopic(device.getDeviceTopic())) {
        if (!obj.containsKey("deviceID") || !obj.containsKey("roomID"))
            return;

        long deviceID = obj["deviceID"];
        long roomID = obj["roomID"];

        if (deviceID != device.getID())
            return;

        device.setRoomID(roomID);
    }
}

void MessageForwarder::manageCapabilityReact(const JsonObject &obj) {
    for (auto &cap : device.getCapabilities()) {
        if (equalsTopic(cap->getTopic())) {
            cap->reactToMessage(obj);
            return;
        }
    }
}

void MessageForwarder::forwardMessage(char *topic, DynamicJsonDocument &doc) {
    _topic = topic;
    JsonObject obj = doc.as<JsonObject>();
    manageCreate(obj);
    manageAddDeviceToRoom(obj);
    manageCapabilityReact(obj);
}