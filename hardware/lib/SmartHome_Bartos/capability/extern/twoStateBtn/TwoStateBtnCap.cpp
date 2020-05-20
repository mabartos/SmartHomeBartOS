#include "TwoStateBtnCap.h"

TwoStateBtnCap::TwoStateBtnCap(const uint8_t &pin, const bool &isPullUp) : ExternBtnCap(pin, isPullUp) {}

void TwoStateBtnCap::init() {
    ExternBtnCap::init();
    _previousState = digitalRead(_pin);
}

//TODO
void TwoStateBtnCap::execute() {
    if (!_previousState) {
    }
}

void TwoStateBtnCap::reactToMessage(const JsonObject &obj) {
}