#ifndef CAPABILITY_UTILS_H
#define CAPABILITY_UTILS_H

#include <string>

#include "capability/CapabilityType.h"
using namespace std;

namespace CapabilityUtils {
static string getTopic(const CapabilityType &capType);
}

static string CapabilityUtils::getTopic(const CapabilityType &capType) {
    string result;
    switch (capType) {
        case CapabilityType::NONE:
            result = "none";
            break;
        case CapabilityType::TEMPERATURE:
            result = "temp";
            break;
        case CapabilityType::HUMIDITY:
            result = "hum";
            break;
        case CapabilityType::HEATER:
            result = "heater";
            break;
        case CapabilityType::LIGHT:
            result = "light";
            break;
        case CapabilityType::RELAY:
            result = "relay";
            break;
        case CapabilityType::SOCKET:
            result = "socket";
            break;
        case CapabilityType::PIR:
            result = "pir";
            break;
        case CapabilityType::GAS_SENSOR:
            result = "gas";
            break;
        case CapabilityType::STATISTICS:
            result = "stats";
            break;
        case CapabilityType::AIR_CONDITIONER:
            result = "ac";
            break;
        case CapabilityType::OTHER:
            result = "other";
            break;
    }
    return result;
}

#endif  // CAPABILITY_UTILS_H