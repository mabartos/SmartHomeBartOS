#include "MqttClient.h"

#include "ESP8266TrueRandom.h"

MqttClient::MqttClient(const string &ssid, const string &password, const string &brokerURL, PubSubClient &mqttClient) : _mqttClient(mqttClient) {
    _ssid = ssid;
    _password = password;
    _brokerURL = brokerURL;
    _lastReconnectAttempt = 0;
}

void MqttClient::init() {
    setupWifi();
    _mqttClient.setServer(_brokerURL.c_str(), PORT);
    reconnect();
}

void MqttClient::setupWifi() {
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
    //TODO UUID
    if (_mqttClient.connect("UUID")) {
        _mqttClient.subscribe("#");
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