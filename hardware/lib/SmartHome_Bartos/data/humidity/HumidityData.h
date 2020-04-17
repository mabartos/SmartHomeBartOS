#ifndef HUMIDITY_DATA_H
#define HUMIDITY_DATA_H
#include "data/CapabilityData.h"

class HumidityData : public CapabilityData {
   private:
    uint8_t _actual;

   public:
    HumidityData(const long &id, const string &name);
    ~HumidityData() = default;

    uint8_t getActual();
    void setActual(const uint8_t &actual);

    DynamicJsonDocument toJSON();
};

#endif  // Humidity_DATA_H