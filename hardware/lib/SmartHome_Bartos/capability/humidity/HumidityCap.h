#ifndef HUMIDITY_CAP_H
#define HUMIDITY_CAP_H

#include <Adafruit_Sensor.h>
#include <DHT.h>

#include "capability/CapabilityDeps.h"
#include "device/Device_deps.h"

class HumidityCap : public CapabilityWithValue {
   private:
    DHT &_dht;

   public:
    HumidityCap(const uint8_t &pin, DHT &dht);
    ~HumidityCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  // HUMIDITY_CAP_H