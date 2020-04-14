
#include "CapabilityWithState.h"

CapabilityWithState::CapabilityWithState(const uint8_t &pin) : Capability(pin) {
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