#ifndef CAPABILITY_WITH_STATE_H
#define CAPABILITY_WITH_STATE_H

#include "Capability.h"
#include "GeneralDeps.h"

class CapabilityWithState : public Capability {
   protected:
    bool _isTurnedOn;
    CapabilityWithState(const uint8_t &pin);

   public:
    ~CapabilityWithState() = default;

    bool isTurnedOn();

    void setState(const bool &isTurnedOn);

    void changeState();
};

#endif  //CAPABILITY_WITH_STATE_H