#include "HumidityData.h"

HumidityData::HumidityData(const long &id, const string &name) : CapabilityData(id, name) {
}

uint8_t HumidityData::getActual() {
    return _actual;
}

void HumidityData::setActual(const uint8_t &actual) {
    if (actual >= 0 && actual <= 100) {
        _actual = actual;
    }
}

DynamicJsonDocument HumidityData::toJSON() {
    DynamicJsonDocument doc = getJSON();
    doc["actual"] = getActual();
    return doc;
}