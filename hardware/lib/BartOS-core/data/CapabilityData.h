#ifndef CAPABILITY_DATA_H
#define CAPABILITY_DATA_H

#include <string>

#include "ArduinoJson.h"

using namespace std;

class CapabilityData {
   protected:
    long _id;
    size_t _jsonSize;
    virtual DynamicJsonDocument getJSON();

   public:
    CapabilityData(const long &id);
    ~CapabilityData() = default;

    long getID();
    void setID(const long &id);

    size_t getJsonSize();

    virtual DynamicJsonDocument toJSON();
};

#endif  // CAPABILITY_DATA_H