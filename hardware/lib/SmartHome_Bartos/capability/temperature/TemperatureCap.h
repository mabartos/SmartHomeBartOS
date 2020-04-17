#ifndef TEMPERATURE_CAP_H
#define TEMPERATURE_CAP_H

#include "capability/CapabilityWithValue.h"
#include "device/Device_deps.h"

class TemperatureCap : public CapabilityWithValue {
   public:
    TemperatureCap(const uint8_t &pin);
    ~TemperatureCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  // TEMPERATURE_CAP_H