#include "HumidityCap.h"

#include "data/humidity/HumidityData.h"

extern Device device;

HumidityCap::HumidityCap(const uint8_t &pin, DHT &dht) : CapabilityWithValue(pin), _dht(dht) {
    _type = CapabilityType::HUMIDITY;
    setName(getRandomName());
}

//TODO
void HumidityCap::init() {
    _dht.begin();
    Serial.println("HUM_INIT");
}

void HumidityCap::execute() {
    if (!executeAfterTime(5) || _ID == -1 || device.getRoomID() == -1)
        return;

    HumidityData data(_ID, _name);

    float hum = _dht.readHumidity();
    if (!isnan(hum)) {
        _value = (int)hum;
    } else
        return;

    data.setActual(_value);

    publishValues(data);
    Serial.println("HUM_EXEC");
}

void HumidityCap::reactToMessage(const JsonObject &obj) {
    Serial.println("HUM_REACT");
}
