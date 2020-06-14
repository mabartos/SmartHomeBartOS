#ifndef TEMPERATURE_CAP_H
#define TEMPERATURE_CAP_H

#include <Adafruit_Sensor.h>
#include <DHT.h>

#include "capability/general/CapabilityDeps.h"
#include "device/Device_deps.h"

class TemperatureCap : public CapabilityWithValue {
   private:
    DHT &_dht;

   public:
    TemperatureCap(const uint8_t &pin, DHT &dht);
    ~TemperatureCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  // TEMPERATURE_CAP_H