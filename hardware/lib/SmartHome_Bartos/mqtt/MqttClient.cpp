#include "MqttClient.h"

MqttClient::MqttClient(const string &ssid, const string &password, const string &brokerURL, PubSubClient &mqttClient) : _mqttClient(mqttClient) {
    _ssid = ssid;
    _password = password;
    _brokerURL = brokerURL;
    _lastReconnectAttempt = 0;
}

void MqttClient::init() {
    setupWifi();
    _mqttClient.setServer(_brokerURL.c_str(), PORT);
}

void MqttClient::setupWifi() {
    delay(10);
    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(_ssid.c_str());

    WiFi.begin(_ssid.c_str(), _password.c_str());

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    randomSeed(micros());

    Serial.println("");
    Serial.println("WiFi connected");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());
}

bool MqttClient::reconnect() {
    if (_mqttClient.connect("arduinoClient")) {
        // Once connected, publish an announcement...
        _mqttClient.publish("outTopic", "hello world");
        // ... and resubscribe
        _mqttClient.subscribe("inTopic");
    }
    return _mqttClient.connected();
}

void MqttClient::checkAvailability() {
    if (!_mqttClient.connected()) {
        long now = millis();
        if (now - _lastReconnectAttempt > 5000) {
            _lastReconnectAttempt = now;
            // Attempt to reconnect
            if (reconnect()) {
                _lastReconnectAttempt = 0;
            }
        }
    } else {
        // Client connected
        _mqttClient.loop();
    }
}

string MqttClient::getBrokerURL() {
    return _brokerURL;
}

PubSubClient &MqttClient::getMQTT() {
    return _mqttClient;
}