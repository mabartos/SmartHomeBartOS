#include "HumidityCap.h"

#include "data/humidity/HumidityData.h"

extern Device device;

HumidityCap::HumidityCap(const uint8_t &pin, DHT &dht) : CapabilityWithValue(pin, CapabilityType::HUMIDITY), _dht(dht) {}

//TODO
void HumidityCap::init() {
    _dht.begin();
    Serial.println("HUM_INIT");
}

void HumidityCap::execute() {
    if (!executeAfterTime(3) || _ID == -1 || device.getRoomID() == -1)
        return;

    HumidityData data(_ID);

    float hum = _dht.readHumidity();
    if (!isnan(hum)) {
        _value = (int)hum;
    } else
        return;

    data.setActual(_value);

    publishValues(data);
}

void HumidityCap::reactToMessage(const JsonObject &obj) {
    Serial.println("HUM_REACT");
}
