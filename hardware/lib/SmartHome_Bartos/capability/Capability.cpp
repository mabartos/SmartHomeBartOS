#include "Capability.h"

#include "capability/utils/CapabilityUtils.h"

Capability::Capability(const uint8_t &pin) : _pin(pin) {
}

long Capability::getID() {
    return _ID;
}
void Capability::setID(const long &id) {
    _ID = id;
}

void Capability::init() {
}

void Capability::execute() {}

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

/* JSON */
void Capability::editCreateCapNested(JsonObject &nested) {
    CapabilityUtils util(_type);
    nested.clear();
    const char *name = util.getTopic();
    const char *type = util.getName();

    nested["name"] = name;
    nested["type"] = type;
}