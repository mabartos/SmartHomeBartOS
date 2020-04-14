#include "HumidityCap.h"

HumidityCap::HumidityCap(const uint8_t &pin) : CapabilityWithValue(pin) {
    _type = CapabilityType::HUMIDITY;
}

//TODO
void HumidityCap::init() {
    Serial.println("HUM_INIT");
}
//TODO

void HumidityCap::execute() {
    Serial.println("HUM_EXEC");
    delay(2000);
}
