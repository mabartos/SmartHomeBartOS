#ifndef CAPABILITY_DATA_H
#define CAPABILITY_DATA_H

#include <string>

using namespace std;

#include "ArduinoJson.h"

class CapabilityData {
   protected:
    long _id;
    string _name;
    size_t _jsonSize;
    virtual DynamicJsonDocument getJSON();

   public:
    CapabilityData(const long &id, const string &name);
    ~CapabilityData() = default;

    long getID();
    void setID(const long &id);

    string getName();
    void setName(const string &name);

    size_t getJsonSize();

    virtual DynamicJsonDocument toJSON();
};

#endif  // CAPABILITY_DATA_H