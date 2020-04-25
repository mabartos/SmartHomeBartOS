#ifndef CAPABILITY_H
#define CAPABILITY_H

#include <ArduinoJson.h>

#include <cstdint>

#include "CapabilityType.h"
#include "data/CapabilityData.h"

using namespace std;

class Capability {
   protected:
    long _ID = -1;
    uint8_t _pin;
    bool _enable;
    CapabilityType _type;

    unsigned long _lastExecution;
    string getRandomName();

   public:
    Capability(const uint8_t &pin, CapabilityType type);
    ~Capability() = default;

    virtual void init();
    virtual void execute();
    virtual void reactToMessage(const JsonObject &obj);

    void publishValues(CapabilityData &data);

    long getID();
    void setID(const long &id);

    uint8_t getPin();
    void setPin(const uint8_t &pin);

    CapabilityType getType();
    void setType(CapabilityType &type);

    bool isEnabled();

    string getTopic();

    bool executeAfterTime(unsigned seconds);

    /* JSON */
    void editCreateCapNested(JsonObject &nested);
};

#endif  // CAPABILITY_H