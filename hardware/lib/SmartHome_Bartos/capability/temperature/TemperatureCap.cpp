
#include "TemperatureCap.h"

#include "data/temperature/TemperatureData.h"
#include "mqtt/MqttClient.h"

extern MqttClient client;
extern Device device;

TemperatureCap::TemperatureCap(const uint8_t &pin, DHT &dht) : CapabilityWithValue(pin, CapabilityType::TEMPERATURE), _dht(dht) {
    setName(getRandomName());
}

void TemperatureCap::init() {
    _dht.begin();
    Serial.println("TEMP_INIT");
}

void TemperatureCap::execute() {
    if (!executeAfterTime(3) || _ID == -1 || device.getRoomID() == -1)
        return;

    TemperatureData data(_ID, _name);
    float temp = _dht.readTemperature();

    if (!isnan(temp)) {
        _value = (int)temp;
    } else
        return;

    data.setActualTemp(_value);

    publishValues(data);

    Serial.println("TEMP_EXEC-2");
}

void TemperatureCap::reactToMessage(const JsonObject &obj) {
    Serial.println("TEMP_REACT");
}
