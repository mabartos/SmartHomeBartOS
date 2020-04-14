#ifndef MQTT_CLIENT_H
#define MQTT_CLIENT_H

#include "GeneralDeps.h"

using namespace std;

class MqttClient {
   private:
    const uint16_t PORT = 1883;
    string _ssid;
    string _password;
    string _brokerURL;
    char _msg[100];

    long _lastReconnectAttempt;

    PubSubClient &_mqttClient;

    void setupWifi();

   public:
    MqttClient(const string &ssid, const string &password, const string &brokerURL, PubSubClient &mqttClient);
    ~MqttClient() = default;

    void init();
    bool reconnect();
    void checkAvailability();

    string getBrokerURL();

    PubSubClient &getMQTT();
};

#endif  //MQTT_CLIENT_H