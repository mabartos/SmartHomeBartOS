#ifndef MESSAGE_FORWARDER_H
#define MESSAGE_FORWARDER_H

using namespace std;
#include <Arduino.h>
#include <ArduinoJson.h>

class MessageForwarder {
   private:
    char *_topic;

    void manageAddDeviceToRoom(const JsonObject &obj);
    void manageCreate(const JsonObject &obj);
    void manageCapabilityReact(const JsonObject &obj);

    bool equalsTopic(const char *receiveTopic);
    bool equalsTopic(const string &receiveTopic);

   public:
    MessageForwarder() = default;
    ~MessageForwarder() = default;

    void forwardMessage(char *topic, DynamicJsonDocument &doc);
};

#endif