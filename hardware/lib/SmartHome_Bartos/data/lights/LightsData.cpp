#include "LightsData.h"

LightsData::LightsData(const long &id) : CapabilityDataWithState(id) {
}

uint8_t LightsData::getIntensity() {
    return _intensity;
}

void LightsData::setIntensity(const uint8_t &intensity) {
    if (intensity >= 0 && intensity <= 100)
        _intensity = intensity;
}

uint8_t LightsData::getMinIntensity() {
    return _minIntensity;
}

void LightsData::setMinIntensity(const uint8_t &minIntensity) {
    if (minIntensity >= 0 && minIntensity <= 100)
        _minIntensity = minIntensity;
}

DynamicJsonDocument LightsData::toJSON() {
    StaticJsonDocument<300> doc;
    doc["intensity"] = getIntensity();
    doc["minIntensity"] = getMinIntensity();

    return doc;
}