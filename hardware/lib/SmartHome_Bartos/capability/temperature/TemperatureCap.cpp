
#include "TemperatureCap.h"

#include "data/temperature/TemperatureData.h"
#include "mqtt/MqttClient.h"

extern MqttClient client;

TemperatureCap::TemperatureCap(const uint8_t &pin) : CapabilityWithValue(pin) {
    _type = CapabilityType::TEMPERATURE;
    setName(getRandomName());
}

void TemperatureCap::init() {
    Serial.println("TEMP_INIT");
}

void TemperatureCap::execute() {
    if (!executeAfterTime(3) || _ID == -1)
        return;

    TemperatureData data(_ID, _name);
    data.setActualTemp(23.3);

    publishValues(data);

    Serial.println("TEMP_EXEC");
}

void TemperatureCap::reactToMessage(const JsonObject &obj) {
    Serial.println("TEMP_REACT");
}
