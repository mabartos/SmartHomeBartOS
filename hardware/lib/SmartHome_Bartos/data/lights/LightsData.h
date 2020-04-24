
#ifndef LIGHTS_DATA_H
#define LIGHTS_DATA_H
#include "data/CapabilityDataWithState.h"

class LightsData : public CapabilityDataWithState {
   private:
    uint8_t _intensity;
    uint8_t _minIntensity;

   public:
    LightsData(const long &id);
    ~LightsData() = default;

    uint8_t getIntensity();
    void setIntensity(const uint8_t &minIntensity);

    uint8_t getMinIntensity();
    void setMinIntensity(const uint8_t &minIntensity);

    DynamicJsonDocument toJSON();
};

#endif