#ifndef EXTERN_BTN_CAP_H
#define EXTERN_BTN_CAP_H

#include "capability/general/CapabilityDeps.h"
#include "device/Device_deps.h"

class ExternBtnCap : public CapabilityWithState {
   protected:
    bool _isPullUp;
    bool _previousState = false;

   public:
    ExternBtnCap(const uint8_t &pin, const bool &isPullUp);
    ~ExternBtnCap() = default;

    bool isPullUp();

    void init();
    void execute();
    void reactToMessage(const JsonObject &obj);
};

#endif  //EXTERN_BTN_CAP_H