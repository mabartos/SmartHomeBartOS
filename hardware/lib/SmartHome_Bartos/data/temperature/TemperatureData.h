#ifndef TEMPERATURE_DATA_H
#define TEMPERATURE_DATA_H
#include "data/CapabilityData.h"

class TemperatureData : public CapabilityData {
   private:
    double _actual;

   public:
    TemperatureData(const long &id, const string &name);
    ~TemperatureData() = default;

    double getActualTemp();
    void setActualTemp(const double &actual);

    DynamicJsonDocument toJSON();
};

#endif  // TEMPERATURE_DATA_H