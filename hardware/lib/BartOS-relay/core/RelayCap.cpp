#include "RelayCap.h"

using namespace std;

RelayCap::RelayCap(const uint8_t &pin) : CapabilityWithState(pin, CapabilityType::RELAY) {
}

void RelayCap::init() {
    Serial.println("RELAY_INIT");
    pinMode(_pin, OUTPUT);
    // turned off
    digitalWrite(_pin, HIGH);
}

void RelayCap::execute() {
}

void RelayCap::reactToMessage(const JsonObject &obj) {
    if (obj.containsKey("isTurnedOn")) {
        _isTurnedOn = obj["isTurnedOn"];

        // Relay is active to LOW
        digitalWrite(_pin, !_isTurnedOn);
    }
}