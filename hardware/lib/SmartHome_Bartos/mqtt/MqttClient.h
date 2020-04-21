#ifndef MQTT_CLIENT_H
#define MQTT_CLIENT_H

#include "GeneralDeps.h"

using namespace std;

class MqttClient {
   private:
    const uint16_t PORT = 1883;

    string _uuid;
    string _brokerURL;

    char _msg[100];

    long _lastReconnectAttempt;

    PubSubClient &_mqttClient;

    void setupWifi();

   public:
    MqttClient(PubSubClient &mqttClient);
    ~MqttClient() = default;

    string getUUID();
    void setUUID(string UUID);

    void init(const string &brokerURL);
    bool reconnect();
    void checkAvailability();

    string getBrokerURL();
    void setBrokerURL(string brokerURL);

    PubSubClient &getMQTT();
};

#endif  //MQTT_CLIENT_H