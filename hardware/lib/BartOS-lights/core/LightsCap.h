#ifndef LIGHTS_CAP_H
#define LIGHTS_CAP_H

#include "capability/general/CapabilityDeps.h"
#include "device/Device_deps.h"
#define PWM_RANGE 1024

class LightsCap : public CapabilityWithState {
   private:
    uint8_t _intensity;
    uint8_t _minIntensity;

    bool execChangeIntensity();

   public:
    LightsCap(const uint8_t &pin);
    ~LightsCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  //LIGHTS_CAP_H