
#include "Device.h"

#include "mqtt/MqttClient.h"

extern MqttClient client;

Device::Device() : _name("asd") {
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

vector<shared_ptr<Capability>> Device::getCapabilities() {
    return _capabilities;
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

DynamicJsonDocument Device::getCreateJSON() {
    const size_t capacity = getCreateJSONSize();
    DynamicJsonDocument create(capacity);

    create["msgID"] = "asd";
    create["name"] = _name.c_str();
    JsonArray caps = create.createNestedArray("capabilities");

    for (auto &item : getCapabilities()) {
        JsonObject obj = caps.createNestedObject();
        item->editCreateCapNested(obj);
    }

    return create;
}

size_t Device::getCreateJSONSize() {
    return JSON_ARRAY_SIZE(getCapabilities().size()) + getCapabilities().size() * JSON_OBJECT_SIZE(2) + JSON_OBJECT_SIZE(3);
}

void Device::publishCreateMessage() {
    char buffer[1024];
    size_t size = serializeJson(getCreateJSON(), buffer);
    Serial.println(size);
    Serial.println(buffer);
    client.getMQTT().publish_P("outTopic", buffer, size);
}
