#ifndef CAPABILITY_H
#define CAPABILITY_H

#include <cstdint>

#include "CapabilityType.h"

class Capability {
   protected:
    long _ID;
    uint8_t _pin;
    bool _enable;
    CapabilityType _type;

   public:
    Capability(const uint8_t &pin);
    ~Capability() = default;

    virtual void init();
    virtual void execute();

    long getID();
    void setID(const long &id);

    uint8_t getPin();
    void setPin(const uint8_t &pin);

    CapabilityType getType();
    void setType(CapabilityType type);

    bool isEnabled();
};

#endif  // CAPABILITY_H