#ifndef TWO_STATE_BTN_CAP_H
#define TWO_STATE_BTN_CAP_H

#include "capability/CapabilityDeps.h"
#include "capability/extern/ExternBtnCap.h"
#include "device/Device_deps.h"

class TwoStateBtnCap : public ExternBtnCap {
   private:
    bool _previousState;

   public:
    TwoStateBtnCap(const uint8_t &pin, const bool &isPullUp);
    ~TwoStateBtnCap() = default;

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  //TWO_STATE_BTN_CAP_H