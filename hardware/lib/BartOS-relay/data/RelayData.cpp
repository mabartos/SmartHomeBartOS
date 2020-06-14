#include "RelayData.h"

RelayData::RelayData(const long &id) : CapabilityDataWithState(id) {
}

DynamicJsonDocument RelayData::toJSON() {
    return getJSON();
}