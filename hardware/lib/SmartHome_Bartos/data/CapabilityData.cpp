#include "CapabilityData.h"

#include <memory>

CapabilityData::CapabilityData(const long &id, const string &name) : _id(id), _name(name) {
}

long CapabilityData::getID() {
    return _id;
}

void CapabilityData::setID(const long &id) {
    _id = id;
}

string CapabilityData::getName() {
    return _name;
}

void CapabilityData::setName(const string &name) {
    _name = name;
}

size_t CapabilityData::getJsonSize() {
    return _jsonSize;
}

DynamicJsonDocument CapabilityData::getJSON() {
    DynamicJsonDocument doc(400);
    doc["id"] = getID();
    doc["name"] = getName().c_str();
    return doc;
}

DynamicJsonDocument CapabilityData::toJSON() {
}