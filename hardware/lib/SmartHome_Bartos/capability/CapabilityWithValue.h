#ifndef CAPABILITY_WITH_VALUE_H
#define CAPABILITY_WITH_VALUE_H

#include "Capability.h"
#include "device/Device_deps.h"

class CapabilityWithValue : public Capability {
   protected:
    double _value;
    string _units;

    CapabilityWithValue(const uint8_t &pin);

   public:
    ~CapabilityWithValue() = default;

    double getValue();
    void setValue(const double &value);

    string getUnits();
    void setUnits(const string &units);
};

#endif  // CAPABILITY_WITH_VALUE_H