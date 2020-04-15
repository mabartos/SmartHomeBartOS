#ifndef CAPABILITY_UTILS_H
#define CAPABILITY_UTILS_H

#include <string>

#include "capability/CapabilityType.h"
using namespace std;

class CapabilityUtils {
   private:
    CapabilityType _type;

   public:
    CapabilityUtils(const CapabilityType& capType);
    ~CapabilityUtils() = default;

    char* getTopic();
    char* getName();
};

#endif  // CAPABILITY_UTILS_H