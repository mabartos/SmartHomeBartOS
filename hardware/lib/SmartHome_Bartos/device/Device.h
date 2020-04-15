
#ifndef DEVICE_H
#define DEVICE_H

#include <ArduinoJson.h>

#include <memory>
#include <string>
#include <vector>

#include "capability/Capability.h"

using namespace std;

class Device {
   private:
    string _name;
    long _ID;
    long _homeID;
    long _roomID;

    vector<shared_ptr<Capability>> _capabilities;

   public:
    Device();
    ~Device() = default;

    string getName();
    void setName(const string &name);

    long getID();
    void setID(const long &id);

    long getHomeID();
    void setHomeID(const long &homeID);

    long getRoomID();
    void setRoomID(const long &roomID);

    /* CAPS */
    vector<shared_ptr<Capability>> getCapabilities();

    void addCapability(shared_ptr<Capability> cap);

    void removeCapability(long id);

    void initAllCapabilities();

    void executeAllCapabilities();

    /* JSON */
    DynamicJsonDocument getCreateJSON();

    void publishCreateMessage();

    size_t getCreateJSONSize();
};

#endif  // DEVICE_H