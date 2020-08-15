#ifndef MESSAGE_FORWARDER_H
#define MESSAGE_FORWARDER_H

using namespace std;
#include <Arduino.h>
#include <ArduinoJson.h>

class MessageForwarder {
   private:
    char *_topic;

    void manageAddDeviceToRoom(const JsonObject &obj);
    void manageEraseAll(const JsonObject &obj);
    void manageCapabilityReact(const JsonObject &obj);

    bool equalsTopic(const char *receiveTopic);
    bool equalsTopic(const string &receiveTopic);

   public:
    MessageForwarder() = default;
    ~MessageForwarder() = default;

    static void manageCreateSPIFS(const JsonObject &doc, const long &deviceID);
    static bool containKeys(const JsonObject &obj, vector<string> &keys);
    void forwardMessage(char *topic, DynamicJsonDocument &doc);
};

#endif