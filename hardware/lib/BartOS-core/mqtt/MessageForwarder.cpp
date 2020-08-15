#include "MessageForwarder.h"

#include <LittleFS.h>

#include "FS.h"
#include "device/Device_deps.h"
#include "mqtt/MqttClient.h"
#include "wifiUtils/WifiUtils.h"

extern Device device;
extern const char *CONFIG_FILE;
extern WifiUtils wifiUtils;
extern MqttClient client;

void MessageForwarder::forwardMessage(char *topic, DynamicJsonDocument &doc) {
    _topic = topic;
    JsonObject obj = doc.as<JsonObject>();

    manageAddDeviceToRoom(obj);
    manageEraseAll(obj);

    // LAST - Capabilities
    manageCapabilityReact(obj);
    doc.shrinkToFit();
    doc.garbageCollect();
}

bool MessageForwarder::equalsTopic(const char *receiveTopic) {
    return (strcmp(_topic, receiveTopic) == 0);
}

bool MessageForwarder::equalsTopic(const string &receiveTopic) {
    return (strcmp(_topic, receiveTopic.c_str()) == 0);
}

void MessageForwarder::manageCreateSPIFS(const JsonObject &obj, const long &deviceID) {
    DynamicJsonDocument doc(1520);

    if (LittleFS.begin() && LittleFS.exists(CONFIG_FILE)) {
        File configFile = LittleFS.open(CONFIG_FILE, "r");
        if (configFile) {
            size_t size = configFile.size();

            std::unique_ptr<char[]> buf(new char[size + 100]);
            configFile.readBytes(buf.get(), size);

            DeserializationError err = deserializeJson(doc, buf.get());
            if (err != DeserializationError::Ok) {
                return;
            }

            configFile.close();
        }
    }

    DynamicJsonDocument newDoc(2048);
    newDoc = doc;
    newDoc["deviceID"] = deviceID;
    newDoc["uuid"] = client.getUUID().c_str();

    File configFile = LittleFS.open(CONFIG_FILE, "w");
    if (!configFile) {
        Serial.println("CANNOT WRITE");
        return;
    }

    const char *brokerURL = client.getBrokerURL().c_str();
    newDoc["brokerURL"] = brokerURL;
    if (serializeJson(newDoc, configFile) == 0) {
        Serial.println("ERROR");
        return;
    }
    configFile.close();

    device.setCapsIDFromJSON(obj);

    doc.shrinkToFit();
    newDoc.shrinkToFit();

    doc.garbageCollect();
    newDoc.garbageCollect();
}

void MessageForwarder::manageEraseAll(const JsonObject &obj) {
    if (equalsTopic(device.getEraseAllTopic())) {
        device.eraseAll();
    }
}

void MessageForwarder::manageAddDeviceToRoom(const JsonObject &obj) {
    if (equalsTopic(device.getDeviceTopic())) {
        vector<string> keys{"resp", "deviceID", "roomID"};
        if (!containKeys(obj, keys))
            return;

        long deviceID = obj["deviceID"];
        long roomID = obj["roomID"];

        if (deviceID != device.getID())
            return;

        device.setRoomID(roomID);
        if (device.getRoomTopicWildCard() != "") {
            client.getMQTT().subscribe(device.getRoomTopicWildCard().c_str());
        }
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

bool MessageForwarder::containKeys(const JsonObject &obj, vector<string> &keys) {
    if (keys.size() == 0)
        return false;

    for (string &key : keys) {
        if (!obj.containsKey(key.c_str()))
            return false;
    }
    return true;
}