#include "PIRCap.h"

#include "data/pir/PIRData.h"

extern Device device;

PIRCap::PIRCap(const uint8_t &pin) : CapabilityWithState(pin, CapabilityType::PIR) {
}

void PIRCap::init() {
    Serial.println("PIR_INIT");
    pinMode(_pin, INPUT);
}

void PIRCap::execute() {
    if (_ID == -1 || device.getRoomID() == -1)
        return;

    PIRData data(_ID);

    bool newTurnedOn = digitalRead(_pin);

    if ((_isTurnedOn != newTurnedOn) || _isFirstValue) {
        data.setState(newTurnedOn);
        publishValues(data);
        _isTurnedOn = newTurnedOn;

        if (_isFirstValue)
            _isFirstValue = false;
    }
}

void PIRCap::reactToMessage(const JsonObject &obj) {}