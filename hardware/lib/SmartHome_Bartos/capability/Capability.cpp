#include "Capability.h"

#include "capability/utils/CapabilityUtils.h"
#include "device/Device_deps.h"
#include "generator/NumberGenerator.h"
#include "mqtt/MqttClient.h"

extern Device device;
extern MqttClient client;

Capability::Capability(const uint8_t &pin) : _pin(pin) {
}

long Capability::getID() {
    return _ID;
}
void Capability::setID(const long &id) {
    _ID = id;
}

string Capability::getRandomName() {
    CapabilityUtils utils(_type);
    string topic(utils.getTopic());
    return topic + "_" + NumberGenerator::generateLongToString(10, 99);
}

string Capability::getName() {
    return _name;
}

void Capability::setName(const string &name) {
    _name = name;
}

//VIRTUAL
void Capability::init() {}

void Capability::execute() {}

void Capability::reactToMessage(const JsonObject &obj) {}

void Capability::publishValues(CapabilityData &data) {
    if (getTopic() == "")
        return;
    char buffer[600];
    size_t size = serializeJson(data.toJSON(), buffer);
    client.getMQTT().publish(getTopic().c_str(), buffer, size);
}

uint8_t Capability::getPin() {
    return _pin;
}

void Capability::setPin(const uint8_t &pin) {
    _pin = pin;
}

bool Capability::isEnabled() {
    return _enable;
}

CapabilityType Capability::getType() {
    return _type;
}

void Capability::setType(CapabilityType type) {
    _type = type;
}

string Capability::getTopic() {
    if (device.getRoomTopic() != "" && _ID != -1) {
        CapabilityUtils util(_type);
        string capID = NumberGenerator::longToString(_ID);
        string topic = device.getRoomTopic();
        return (topic + "/" + util.getTopic() + "/" + capID);
    }
    return "";
}

/* JSON */
void Capability::editCreateCapNested(JsonObject &nested) {
    CapabilityUtils util(_type);
    nested.clear();
    const char *name = getName().c_str();
    const char *type = util.getName();

    nested["name"] = name;
    nested["type"] = type;
}

bool Capability::executeAfterTime(unsigned seconds) {
    if ((millis() - _lastExecution) >= (seconds * 1000)) {
        _lastExecution = millis();
        return true;
    }
    return false;
}