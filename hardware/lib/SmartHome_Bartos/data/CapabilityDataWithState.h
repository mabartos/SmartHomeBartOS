#ifndef CAPABILITY_STATE_H
#define CAPABILITY_STATE_H

#include "data/CapabilityData.h"

class CapabilityDataWithState : public CapabilityData {
   protected:
    bool _isTurnedOn;

    DynamicJsonDocument getJSON() override;

   public:
    CapabilityDataWithState(const long &id, const string &name);
    ~CapabilityDataWithState() = default;

    bool isTurnedOn();
    void setState(const bool &isTurnedOn);
};

#endif  //CAPABILITY_STATE_H