#include "CapabilityUtils.h"

#include <string.h>

using namespace std;

CapabilityUtils::CapabilityUtils(const CapabilityType& capType) : _type(capType) {
}

char* CapabilityUtils::getName() {
    char* result;
    switch (_type) {
        case CapabilityType::NONE:
            result = "NONE";
            break;
        case CapabilityType::TEMPERATURE:
            result = "TEMPERATURE";
            break;
        case CapabilityType::HUMIDITY:
            result = "HUMIDITY";
            break;
        case CapabilityType::HEATER:
            result = "HEATER";
            break;
        case CapabilityType::LIGHT:
            result = "LIGHT";
            break;
        case CapabilityType::RELAY:
            result = "RELAY";
            break;
        case CapabilityType::SOCKET:
            result = "SOCKET";
            break;
        case CapabilityType::PIR:
            result = "PIR";
            break;
        case CapabilityType::GAS_SENSOR:
            result = "GAS_SENSOR";
            break;
        case CapabilityType::STATISTICS:
            result = "STATISTICS";
            break;
        case CapabilityType::AIR_CONDITIONER:
            result = "AIR_CONDITIONER";
            break;
        case CapabilityType::OTHER:
            result = "OTHER";
            break;
    }
    return result;
}

char* CapabilityUtils::getTopic() {
    char* result;
    switch (_type) {
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

CapabilityType CapabilityUtils::getFromString(const string& type) {
    return CapabilityType::HUMIDITY;
}

bool CapabilityUtils::isEqual(const string& str, CapabilityType type) {
    CapabilityUtils utils(type);
    return (strcmp(str.c_str(), utils.getName()) == 0);
}