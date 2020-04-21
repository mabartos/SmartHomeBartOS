#include "MessageForwarder.h"

#include "FS.h"
#include "device/Device_deps.h"
#include "mqtt/MqttClient.h"
#include "wifiUtils/WifiUtils.h"

extern Device device;
extern const char *CONFIG_FILE;
extern WifiUtils wifiUtils;
extern MqttClient client;

bool MessageForwarder::equalsTopic(const char *receiveTopic) {
    return (strcmp(_topic, receiveTopic) == 0);
}

bool MessageForwarder::equalsTopic(const string &receiveTopic) {
    return (strcmp(_topic, receiveTopic.c_str()) == 0);
}

void MessageForwarder::manageCreate(const JsonObject &obj) {
    if (equalsTopic(device.getHomeTopic())) {
        vector<string> keys{"msgID", "id", "name", "capabilities"};
        if (!containKeys(obj, keys) || (obj.containsKey("code") && obj["code"] != 200))
            return;

        long msgID = obj["msgID"];

        if (msgID == device.getManageMsgID()) {
            long ID = obj["id"];
            device.setID(ID);

            DynamicJsonDocument doc = wifiUtils.getConfigDoc();
            doc["deviceID"] = ID;
            doc["uuid"] = client.getUUID().c_str();

            //TODO could be more encapsulated
            File configFile = SPIFFS.open(CONFIG_FILE, "w");
            if (!configFile) {
                Serial.println("CANNOT WRITE");
                return;
            }

            serializeJson(doc, Serial);
            if (serializeJson(doc, configFile) == 0) {
                Serial.println("CANNOT SERIALIZE");
                return;
            }
            configFile.close();

            device.setCapsIDFromJSON(obj);
            device.setInitialized(true);
        }
    }
}

void MessageForwarder::manageConnect(const JsonObject &obj) {
    if (equalsTopic(device.getHomeTopic())) {
        vector<string> keys{"msgID", "id", "name", "roomID", "capabilities"};
        if (!containKeys(obj, keys) || (obj.containsKey("code") && obj["code"] != 200))
            return;

        long msgID = obj["msgID"];
        long id = obj["id"];
        if (msgID != device.getManageMsgID() || id != device.getID())
            return;

        const char *name = obj["name"];
        long roomID = obj["roomID"];
        if (strcmp(name, device.getName().c_str()) != 0) {
            device.setName(string(name));
        }
        if (roomID != device.getRoomID()) {
            device.setRoomID(roomID);
        }

        device.setCapsIDFromJSON(obj);
        device.setInitialized(true);
    }
}

void MessageForwarder::manageAddDeviceToRoom(const JsonObject &obj) {
    if (equalsTopic(device.getDeviceTopic())) {
        vector<string> keys{"deviceID", "roomID"};
        if (!containKeys(obj, keys))
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
    manageConnect(obj);
    manageAddDeviceToRoom(obj);
    manageCapabilityReact(obj);
}

bool MessageForwarder::containKeys(const JsonObject &obj, vector<string> &keys) {
    if (keys.size() == 0)
        return false;

    for (string &key : keys) {
        if (!obj.containsKey(key.c_str()))
            return false;
    }
    return true;
}