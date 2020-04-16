#include "MessageForwarder.h"

#include "device/Device_deps.h"

extern Device device;

void MessageForwarder::manageCreate(DynamicJsonDocument &doc) {
    if (strcmp(_topic, device.getHomeTopic().c_str()) == 0) {
        JsonObject obj = doc.as<JsonObject>();

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

void MessageForwarder::forwardMessage(char *topic, DynamicJsonDocument &doc) {
    _topic = topic;
    manageCreate(doc);
}