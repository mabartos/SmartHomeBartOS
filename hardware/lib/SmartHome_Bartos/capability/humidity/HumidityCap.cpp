#include "HumidityCap.h"

HumidityCap::HumidityCap(const uint8_t &pin) : CapabilityWithValue(pin) {
    _type = CapabilityType::HUMIDITY;
    setName(getRandomName());
}

//TODO
void HumidityCap::init() {
    Serial.println("HUM_INIT");
}
//TODO

void HumidityCap::execute() {
    Serial.println("HUM_EXEC");
}

void HumidityCap::reactToMessage(const JsonObject &obj) {
    Serial.println("HUM_REACT");
}
