#include "PIRData.h"

PIRData::PIRData(const long &id) : CapabilityDataWithState(id) {
}

DynamicJsonDocument PIRData::toJSON() {
    return getJSON();
}