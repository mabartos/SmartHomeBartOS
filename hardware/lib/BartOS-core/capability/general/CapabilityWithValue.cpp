#include "capability/general/CapabilityWithValue.h"

CapabilityWithValue::CapabilityWithValue(const uint8_t &pin, CapabilityType type) : Capability(pin, type) {
}

double CapabilityWithValue::getValue() {
    return _value;
}
void CapabilityWithValue::setValue(const double &value) {
    _value = value;
}

string CapabilityWithValue::getUnits() {
    return _units;
}
void CapabilityWithValue::setUnits(const string &units) {
    _units = units;
}