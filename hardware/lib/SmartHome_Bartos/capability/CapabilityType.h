#ifndef CAPABILITY_TYPE_H
#define CAPABILITY_TYPE_H

#include <vector>
using namespace std;

enum class CapabilityType {
    NONE,
    TEMPERATURE,
    HUMIDITY,
    HEATER,
    LIGHT,
    RELAY,
    SOCKET,
    PIR,
    GAS_SENSOR,
    STATISTICS,
    AIR_CONDITIONER,
    OTHER
};

const vector<CapabilityType> allCapabilities{
    CapabilityType::NONE,
    CapabilityType::TEMPERATURE,
    CapabilityType::HUMIDITY,
    CapabilityType::HEATER,
    CapabilityType::LIGHT,
    CapabilityType::RELAY,
    CapabilityType::SOCKET,
    CapabilityType::PIR,
    CapabilityType::GAS_SENSOR,
    CapabilityType::STATISTICS,
    CapabilityType::AIR_CONDITIONER,
    CapabilityType::OTHER};

#endif  //CAPABILITY_TYPE_H