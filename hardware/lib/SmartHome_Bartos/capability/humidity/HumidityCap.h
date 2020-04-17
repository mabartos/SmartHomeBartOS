#ifndef HUMIDITY_CAP_H
#define HUMIDITY_CAP_H

#include "capability/CapabilityDeps.h"
#include "device/Device_deps.h"

class HumidityCap : public CapabilityWithValue {
   public:
    HumidityCap(const uint8_t &pin);
    ~HumidityCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  // HUMIDITY_CAP_H