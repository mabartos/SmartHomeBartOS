#include "TemperatureCap.h"

#include "data/TemperatureData.h"
#include "mqtt/MqttClient.h"

extern MqttClient client;
extern Device device;

TemperatureCap::TemperatureCap(const uint8_t &pin, DHT &dht) : CapabilityWithValue(pin, CapabilityType::TEMPERATURE), _dht(dht) {
}

void TemperatureCap::init() {
    _dht.begin();
    Serial.println("TEMP_INIT");
}

void TemperatureCap::execute() {
    if (executeAfterTime(3)) {
        if (_ID == -1 || device.getRoomID() == -1)
            return;

        TemperatureData data(_ID);
        float temp = _dht.readTemperature();

        if (!isnan(temp)) {
            _value = (int)temp;
        } else
            return;

        data.setActualTemp(_value);

        publishValues(data);
    }
}

void TemperatureCap::reactToMessage(const JsonObject &obj) {
}