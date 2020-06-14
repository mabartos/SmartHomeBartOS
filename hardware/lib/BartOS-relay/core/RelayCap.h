#ifndef RELAY_CAP_H
#define RELAY_CAP_H

#include "capability/general/CapabilityDeps.h"
#include "device/Device_deps.h"

class RelayCap : public CapabilityWithState {
   public:
    RelayCap(const uint8_t &pin);
    ~RelayCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  //RELAY_CAP_H