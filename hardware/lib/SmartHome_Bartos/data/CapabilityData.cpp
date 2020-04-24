#include "CapabilityData.h"

#include <memory>

CapabilityData::CapabilityData(const long &id) : _id(id) {
}

long CapabilityData::getID() {
    return _id;
}

void CapabilityData::setID(const long &id) {
    _id = id;
}

size_t CapabilityData::getJsonSize() {
    return _jsonSize;
}

DynamicJsonDocument CapabilityData::getJSON() {
    DynamicJsonDocument doc(400);
    doc["id"] = getID();
    return doc;
}

DynamicJsonDocument CapabilityData::toJSON() {
}