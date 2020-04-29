#ifndef RELAY_DATA_H
#define RELAY_DATA_H

#include "ArduinoJson.h"
#include "data/CapabilityDataWithState.h"

class RelayData : public CapabilityDataWithState {
   public:
    RelayData(const long &id);
    ~RelayData() = default;

    DynamicJsonDocument toJSON();
};

#endif  //RELAY_DATA_H