
#include "CapabilityWithState.h"

CapabilityWithState::CapabilityWithState(const uint8_t &pin, CapabilityType type) : Capability(pin, type) {
}

bool CapabilityWithState::isTurnedOn() {
    return _isTurnedOn;
}

void CapabilityWithState::setState(const bool &isTurnedOn) {
    _isTurnedOn = isTurnedOn;
}

void CapabilityWithState::changeState() {
    _isTurnedOn = !_isTurnedOn;
}