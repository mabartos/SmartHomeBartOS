#include "CapabilityDataWithState.h"

CapabilityDataWithState::CapabilityDataWithState(const long &id, const string &name) : CapabilityData(id, name) {
}

bool CapabilityDataWithState::isTurnedOn() {
    return _isTurnedOn;
}
void CapabilityDataWithState::setState(const bool &isTurnedOn) {
    _isTurnedOn = isTurnedOn;
}

DynamicJsonDocument CapabilityDataWithState::getJSON() {
    DynamicJsonDocument doc = CapabilityData::getJSON();
    doc["isTurnedOn"] = isTurnedOn();
    return doc;
}