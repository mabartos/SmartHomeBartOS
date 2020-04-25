#include "LightsCap.h"

#include "data/lights/LightsData.h"
#include "mqtt/MqttClient.h"

extern MqttClient client;
extern Device device;

LightsCap::LightsCap(const uint8_t &pin) : CapabilityWithState(pin, CapabilityType::LIGHT) {
}

void LightsCap::init() {
    pinMode(_pin, OUTPUT);
}

bool LightsCap::execChangeIntensity() {
    if (_intensity >= 0 && _intensity <= 100) {
        float resultIntensity = (PWM_RANGE / 100) * _intensity;
        analogWrite(_pin, (uint16_t)resultIntensity);
        return true;
    }
    return false;
}

void LightsCap::execute() {
}

void LightsCap::reactToMessage(const JsonObject &obj) {
    if (!obj.containsKey("isTurnedOn") || !obj.containsKey("intensity") || !obj.containsKey("minIntensity"))
        return;

    _isTurnedOn = obj["isTurnedOn"];
    _intensity = obj["intensity"];
    _minIntensity = obj["minIntensity"];

    if (_isTurnedOn) {
        execChangeIntensity();
    } else {
        digitalWrite(_pin, _isTurnedOn);
    }
}
