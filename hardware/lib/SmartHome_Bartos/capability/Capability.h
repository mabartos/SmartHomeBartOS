#ifndef CAPABILITY_H
#define CAPABILITY_H

#include <ArduinoJson.h>

#include <cstdint>

#include "CapabilityType.h"

using namespace std;

class Capability {
   protected:
    long _ID = -1;
    string _name;
    uint8_t _pin;
    bool _enable;
    CapabilityType _type;

    string getRandomName();

   public:
    Capability(const uint8_t &pin);
    ~Capability() = default;

    virtual void init();
    virtual void execute();
    virtual void reactToMessage(const JsonObject &obj);

    long getID();
    void setID(const long &id);

    string getName();
    void setName(const string &name);

    uint8_t getPin();
    void setPin(const uint8_t &pin);

    CapabilityType getType();
    void setType(CapabilityType type);

    bool isEnabled();

    string getTopic();

    /* JSON */
    void editCreateCapNested(JsonObject &nested);
};

#endif  // CAPABILITY_H