#ifndef PIR_CAP_H
#define PIR_CAP_H

#include "capability/CapabilityDeps.h"
#include "device/Device_deps.h"

class PIRCap : public CapabilityWithState {
   private:
    bool _isFirstValue = true;

   public:
    PIRCap(const uint8_t &pin);
    ~PIRCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  //PIR_CAP_H