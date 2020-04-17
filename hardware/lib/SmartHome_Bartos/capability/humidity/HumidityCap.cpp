#include "HumidityCap.h"

#include "data/humidity/HumidityData.h"

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
    if (!executeAfterTime(5) || _ID == -1)
        return;

    HumidityData data(_ID, _name);
    data.setActual(55);

    publishValues(data);
    Serial.println("HUM_EXEC");
}

void HumidityCap::reactToMessage(const JsonObject &obj) {
    Serial.println("HUM_REACT");
}
