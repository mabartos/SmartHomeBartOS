#ifndef CAPABILITY_UTILS_H
#define CAPABILITY_UTILS_H

#include <ArduinoJson.h>

#include <string>

#include "capability/general/CapabilityType.h"

class CapabilityUtils {
   private:
    CapabilityType _type;

    bool isEqual(const string& str, CapabilityType type);

   public:
    CapabilityUtils(CapabilityType capType);
    ~CapabilityUtils() = default;

    static CapabilityType getFromString(const string& type);
    const char* getTopic();
    const char* getName();

    static const char* getTopic(CapabilityType type);
    static const char* getName(CapabilityType type);

    static void setTypeJSON(JsonObject& obj, CapabilityType type);
};

#endif  // CAPABILITY_UTILS_H