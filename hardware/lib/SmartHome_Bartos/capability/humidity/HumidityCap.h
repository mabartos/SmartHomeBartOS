#ifndef HUMIDITY_CAP_H
#define HUMIDITY_CAP_H

#include "capability/CapabilityWithValue.h"
#include "device/Device_deps.h"

class HumidityCap : public CapabilityWithValue {
   public:
    HumidityCap(const uint8_t &pin);
    ~HumidityCap() = default;

    void init();
    void execute();
};

#endif  // HUMIDITY_CAP_H