
#include "Device.h"

#include <string>

#include "capability/utils/CapabilityUtils.h"
#include "mqtt/MqttClient.h"

extern MqttClient client;

Device::Device() {
    setName("Dev_" + NumberGenerator::generateIntToString(2000, 9999));
}

string Device::getName() {
    return _name;
}

void Device::setName(const string &name) {
    _name = name;
}

long Device::getID() {
    return _ID;
}
void Device::setID(const long &id) {
    _ID = id;
}

long Device::getHomeID() {
    return _homeID;
}
void Device::setHomeID(const long &homeID) {
    _homeID = homeID;
}

long Device::getRoomID() {
    return _roomID;
}

void Device::setRoomID(const long &roomID) {
    _roomID = roomID;
}

bool Device::isInitialized() {
    return _initialized;
}
void Device::setInitialized(bool initialized) {
    _initialized = initialized;
}

long Device::getManageMsgID() {
    return _manageMsgID;
}

void Device::setManageMsgID(const long &msgID) {
    _manageMsgID = msgID;
}

/* TOPIC */

string Device::getHomeTopic() {
    string topic = "homes/";
    if (_homeID != -1) {
        string homeID = NumberGenerator::longToString(_homeID);
        return (topic + homeID);
    }
    return "";
}

string Device::getRoomTopic() {
    if (_roomID != -1 && getHomeTopic() != "") {
        string roomID = NumberGenerator::longToString(_roomID);
        return (getHomeTopic() + "/rooms/" + roomID);
    }
    return "";
}

string Device::getHomeTopicWildCard() {
    return (getHomeTopic() != "") ? getHomeTopic() + "/#" : nullptr;
}

string Device::getDeviceTopic() {
    if (_ID != -1) {
        string deviceID = NumberGenerator::longToString(_ID);
        return (getHomeTopic() + "/devices/" + deviceID);
    }
    return "";
}

string Device::getCreateTopic() {
    string homeTopic = getHomeTopic();
    if (homeTopic != "") {
        return string(homeTopic + "/create");
    }
    return "";
}

string Device::getConnectTopic() {
    string homeTopic = getHomeTopic();
    if (homeTopic != "") {
        return string(homeTopic + "/connect");
    }
    return "";
}

string Device::getCreateTopicResp() {
    string createTopic = getCreateTopic();
    if (createTopic != "") {
        return string(createTopic + "/resp");
    }
    return "";
}
string Device::getConnectTopicResp() {
    string connectTopic = getConnectTopic();
    if (connectTopic != "") {
        return string(connectTopic + "/resp");
    }
    return "";
}

string Device::getCreateTopicWild() {
    string createTopic = getCreateTopic();
    if (createTopic != "") {
        return string(createTopic + "/#");
    }
    return "";
}

/* CAPS */
vector<shared_ptr<Capability>> Device::getCapabilities() {
    return _capabilities;
}

auto Device::getCapabilityByName(const string &name) -> shared_ptr<Capability> {
    for (auto &item : getCapabilities()) {
        if (item->getName() == name) {
            return item;
        }
    }
    return nullptr;
}

auto Device::getCapByPinAndType(const uint8_t &pin, const CapabilityType &type) -> shared_ptr<Capability> {
    for (auto &item : getCapabilities()) {
        if (item->getPin() == pin && item->getType() == type) {
            return item;
        }
    }
    return nullptr;
}

void Device::addCapability(shared_ptr<Capability> cap) {
    _capabilities.push_back(cap);
}

void Device::removeCapability(long id) {
    vector<shared_ptr<Capability>> caps = getCapabilities();
    for (unsigned i = 0; i < caps.size(); i++) {
        if (caps[i]->getID() == id) {
            _capabilities.erase(caps.begin() + i);
        }
    }
}

void Device::initAllCapabilities() {
    for (auto &item : getCapabilities()) {
        item->init();
    }
}

void Device::executeAllCapabilities() {
    for (auto &item : getCapabilities()) {
        item->execute();
        client.checkAvailability();
    }
}

// CREATE

DynamicJsonDocument Device::getCreateJSON() {
    const size_t capacity = getCreateJSONSize();
    DynamicJsonDocument create(capacity);
    long msgID = NumberGenerator::generateLong(100, 999);
    setManageMsgID(msgID);

    create["msgID"] = msgID;
    create["name"] = _name.c_str();
    JsonArray caps = create.createNestedArray("capabilities");

    for (auto &item : getCapabilities()) {
        JsonObject obj = caps.createNestedObject();
        item->editCreateCapNested(obj);
    }

    serializeJson(create, Serial);
    return create;
}

size_t Device::getCreateJSONSize() {
    return JSON_ARRAY_SIZE(getCapabilities().size()) + getCapabilities().size() * (JSON_OBJECT_SIZE(3) + 100) + JSON_OBJECT_SIZE(4) + 100;
}

void Device::publishCreateMessage() {
    char buffer[1024];

    serializeJson(getCreateJSON(), buffer);

    const char *result = getCreateTopic().c_str();
    client.getMQTT().publish_P(result, buffer, false);
}

// CONNECT

DynamicJsonDocument Device::getConnectJSON() {
    const size_t capacity = getConnectJSONSize();
    DynamicJsonDocument connect(capacity);
    long msgID = NumberGenerator::generateLong(100, 999);
    setManageMsgID(msgID);

    connect["msgID"] = msgID;
    connect["id"] = getID();
    connect["name"] = _name.c_str();

    return connect;
}

size_t Device::getConnectJSONSize() {
    return JSON_OBJECT_SIZE(3) + 80;
}

void Device::publishConnectMessage() {
    char buffer[1024];
    serializeJson(getConnectJSON(), buffer);
    string topic(getConnectTopic() + "/" + NumberGenerator::longToString(getID()));

    client.getMQTT().publish_P(topic.c_str(), buffer, false);
}

void Device::setCapsIDFromJSON(const JsonObject &obj) {
    setCapsIDFromJSON(obj, false);
}

void Device::setCapsIDFromJSON(const JsonObject &obj, bool shouldCreate) {
    if (obj.containsKey("capabilities")) {
        DynamicJsonDocument doc(500);

        JsonArray caps = obj["capabilities"];

        for (JsonObject cap : caps) {
            long capID = cap["id"];
            const char *name = cap["name"];
            uint8_t pin = cap["pin"];
            const char *type = cap["type"];

            auto p_cap = getCapByPinAndType(pin, CapabilityUtils::getFromString(string(type)));
            if (p_cap != nullptr) {
                p_cap->setID(capID);
                p_cap->setName(name);
            }
        }
    }
}
