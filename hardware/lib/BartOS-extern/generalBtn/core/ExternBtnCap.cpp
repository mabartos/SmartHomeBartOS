#include "ExternBtnCap.h"

#include "generalBtn/data/ExternBtnData.h"
extern Device device;

ExternBtnCap::ExternBtnCap(const uint8_t &pin, const bool &isPullUp) : CapabilityWithState(pin, CapabilityType::EXTERN_BTN), _isPullUp(isPullUp){};

void ExternBtnCap::init() {
    pinMode(_pin, _isPullUp ? INPUT_PULLUP : INPUT_PULLDOWN_16);
}

//TODO
void ExternBtnCap::execute() {
    if (_ID == -1 || device.getRoomID() == -1)
        return;

    const bool state = digitalRead(_pin);

    if (state != _previousState) {
        ExternBtnData data(_ID);
        data.setState(state);
        publishValues(data);
        _previousState = state;
    }
}

void ExternBtnCap::reactToMessage(const JsonObject &obj) {
}