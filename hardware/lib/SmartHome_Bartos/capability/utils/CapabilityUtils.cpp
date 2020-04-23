#include "CapabilityUtils.h"

#include <Arduino.h>
#include <string.h>

using namespace std;

CapabilityUtils::CapabilityUtils(CapabilityType capType) : _type(capType) {
}

const char* CapabilityUtils::getName() {
    return getName(_type);
}

const char* CapabilityUtils::getTopic() {
    return getTopic(_type);
}

const char* CapabilityUtils::getTopic(CapabilityType type) {
    string result;
    switch (type) {
        case CapabilityType::NONE:
            result = "none";
            break;
        case CapabilityType::TEMPERATURE:
            result = "temp";
            break;
        case CapabilityType::HUMIDITY:
            result = "humi";
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
    return result.c_str();
}
const char* CapabilityUtils::getName(CapabilityType type) {
    string result;
    switch (type) {
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
    return result.c_str();
}

// Regards the ArduinoJson library, it's necessary to avoid memory leaks by this approach.
void CapabilityUtils::setTypeJSON(JsonObject& obj, CapabilityType type) {
    switch (type) {
        case CapabilityType::NONE:
            obj["type"] = "NONE";
            break;
        case CapabilityType::TEMPERATURE:
            obj["type"] = "TEMPERATURE";
            break;
        case CapabilityType::HUMIDITY:
            obj["type"] = "HUMIDITY";
            break;
        case CapabilityType::HEATER:
            obj["type"] = "HEATER";
            break;
        case CapabilityType::LIGHT:
            obj["type"] = "LIGHT";
            break;
        case CapabilityType::RELAY:
            obj["type"] = "RELAY";
            break;
        case CapabilityType::SOCKET:
            obj["type"] = "SOCKET";
            break;
        case CapabilityType::PIR:
            obj["type"] = "PIR";
            break;
        case CapabilityType::GAS_SENSOR:
            obj["type"] = "GAS_SENSOR";
            break;
        case CapabilityType::STATISTICS:
            obj["type"] = "STATISTICS";
            break;
        case CapabilityType::AIR_CONDITIONER:
            obj["type"] = "AIR_CONDITIONER";
            break;
        case CapabilityType::OTHER:
            obj["type"] = "OTHER";
            break;
    }
}

CapabilityType CapabilityUtils::getFromString(const string& type) {
    for (CapabilityType item : allCapabilities) {
        CapabilityUtils util(item);
        if (strcmp(type.c_str(), util.getName()) == 0) {
            return item;
        }
    }
    return CapabilityType::NONE;
}

bool CapabilityUtils::isEqual(const string& str, CapabilityType type) {
    CapabilityUtils utils(type);
    return (strcmp(str.c_str(), utils.getName()) == 0);
}