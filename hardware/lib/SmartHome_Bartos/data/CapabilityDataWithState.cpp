#include "CapabilityDataWithState.h"

CapabilityDataWithState::CapabilityDataWithState(const long &id) : CapabilityData(id) {
}

bool CapabilityDataWithState::isTurnedOn() {
    return _isTurnedOn;
}
void CapabilityDataWithState::setState(const bool &isTurnedOn) {
    _isTurnedOn = isTurnedOn;
}

DynamicJsonDocument CapabilityDataWithState::getJSON() {
    StaticJsonDocument<500> doc;
    doc["isTurnedOn"] = isTurnedOn();
    return doc;
}