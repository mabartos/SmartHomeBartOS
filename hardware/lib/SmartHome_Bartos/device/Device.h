#ifndef DEVICE_H
#define DEVICE_H

#include <ArduinoJson.h>

#include <memory>
#include <string>
#include <vector>

#include "capability/Capability.h"
#include "generator/NumberGenerator.h"

using namespace std;

class Device {
   private:
    string _name;
    long _ID = -1;
    long _homeID = -1;
    long _roomID = -1;

    int _manageMsgID;

    bool _initialized = false;

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

    long getManageMsgID();
    void setManageMsgID(const long &msgID);

    //TOPIC
    string getHomeTopic();
    string getHomeTopicWildCard();

    string getRoomTopic();

    string getDeviceTopic();

    bool isInitialized();
    void setInitialized(bool initialized);

    /* CAPS */
    vector<shared_ptr<Capability>> getCapabilities();

    void setCapsIDFromJSON(const JsonObject &obj);

    auto getCapabilityByName(const string &name) -> shared_ptr<Capability>;

    auto getCapByPinAndType(const uint8_t &pin, const CapabilityType &type) -> shared_ptr<Capability>;

    void addCapability(shared_ptr<Capability> cap);

    void removeCapability(long id);

    void initAllCapabilities();

    void executeAllCapabilities();

    /* JSON */
    // CREATE
    DynamicJsonDocument getCreateJSON();
    void publishCreateMessage();
    size_t getCreateJSONSize();

    // CONNECT
    DynamicJsonDocument getConnectJSON();
    void publishConnectMessage();
    size_t getConnectJSONSize();
};

#endif  // DEVICE_H