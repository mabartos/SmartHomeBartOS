#ifndef CAPABILITY_UTILS_H
#define CAPABILITY_UTILS_H

#include <string>

#include "capability/CapabilityType.h"
using namespace std;

class CapabilityUtils {
   private:
    CapabilityType _type;

    bool isEqual(const string& str, CapabilityType type);

   public:
    CapabilityUtils(const CapabilityType& capType);
    ~CapabilityUtils() = default;

    static CapabilityType getFromString(const string& type);
    char* getTopic();
    char* getName();
};

#endif  // CAPABILITY_UTILS_H