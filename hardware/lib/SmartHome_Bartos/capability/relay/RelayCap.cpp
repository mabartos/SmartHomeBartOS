#include "RelayCap.h"

#include <string>
#include <vector>

#include "Arduino.h"

using namespace std;

RelayCap::RelayCap(const uint8_t &pin) : CapabilityWithState(pin, CapabilityType::RELAY) {
}

void RelayCap::init() {
    pinMode(_pin, OUTPUT);
}

void RelayCap::execute() {
}

void RelayCap::reactToMessage(const JsonObject &obj) {
    if (obj.containsKey("isTurnedOn")) {
        _isTurnedOn = obj["isTurnedOn"];

        digitalWrite(_pin, _isTurnedOn);
    }
}