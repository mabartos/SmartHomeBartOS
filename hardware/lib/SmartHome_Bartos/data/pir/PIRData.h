#ifndef PIR_DATA_H
#define PIR_DATA_H

#include "ArduinoJson.h"
#include "data/CapabilityDataWithState.h"

class PIRData : public CapabilityDataWithState {
   public:
    PIRData(const long &id);
    ~PIRData() = default;

    DynamicJsonDocument toJSON();
};

#endif  //PIR_DATA_H