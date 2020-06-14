#ifndef EXTERN_BTN_DATA_H
#define EXTERN_BTN_DATA_H

#include "ArduinoJson.h"
#include "data/CapabilityDataWithState.h"

class ExternBtnData : public CapabilityDataWithState {
   public:
    ExternBtnData(const long &id);
    ~ExternBtnData() = default;

    DynamicJsonDocument toJSON();
};

#endif  //EXTERN_BTN_DATA_H