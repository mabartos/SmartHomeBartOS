
#include "Device.h"

#include <string>

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

string Device::getHomeTopic() {
    string topic = "homes/";
    string homeID = NumberGenerator::longToString(_homeID);
    return (topic + homeID);
}

string Device::getHomeTopicWildCard() {
    return (getHomeTopic() + "/#");
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
/*
shared_ptr<Capability> Device::getCapabilityByName(const char *name) {
    string result(name);
    return getCapabilityByName(result);
}*/

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

    return create;
}

size_t Device::getCreateJSONSize() {
    return JSON_ARRAY_SIZE(getCapabilities().size()) + getCapabilities().size() * (JSON_OBJECT_SIZE(2) + 80) + JSON_OBJECT_SIZE(3) + 100;
}

void Device::publishCreateMessage() {
    char buffer[1024];
    size_t size = serializeJson(getCreateJSON(), buffer);

    const char *result = (getHomeTopic() + "/create").c_str();
    client.getMQTT().publish_P(result, buffer, size);
}
