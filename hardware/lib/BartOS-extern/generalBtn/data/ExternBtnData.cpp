#include "ExternBtnData.h"

ExternBtnData::ExternBtnData(const long &id) : CapabilityDataWithState(id) {
}

DynamicJsonDocument ExternBtnData::toJSON() {
    return getJSON();
}