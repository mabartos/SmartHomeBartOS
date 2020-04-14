#ifndef CAPABILITY_TYPE_H
#define CAPABILITY_TYPE_H

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

#endif  //CAPABILITY_TYPE_H