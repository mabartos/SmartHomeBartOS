#ifndef MESSAGE_FORWARDER_H
#define MESSAGE_FORWARDER_H

using namespace std;
#include <Arduino.h>
#include <ArduinoJson.h>

class MessageForwarder {
   private:
    char *_topic;

    void manageCreate(DynamicJsonDocument &doc);

   public:
    MessageForwarder() = default;
    ~MessageForwarder() = default;

    void forwardMessage(char *topic, DynamicJsonDocument &doc);
};

#endif