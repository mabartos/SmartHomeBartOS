#include "MqttClient.h"

#include "ESP8266TrueRandom.h"

extern Device device;

MqttClient::MqttClient(PubSubClient &mqttClient) : _mqttClient(mqttClient) {
    _lastReconnectAttempt = 0;
}

void MqttClient::init(const string &brokerURL) {
    _brokerURL = brokerURL;
    _mqttClient.setServer(_brokerURL.c_str(), PORT);
    reconnect();
}

string MqttClient::getUUID() {
    return _uuid;
}
void MqttClient::setUUID(string UUID) {
    _uuid = UUID;
}

bool MqttClient::reconnect() {
    if (_mqttClient.connect(getUUID().c_str(), device.getLogoutTopic().c_str(), 1, false, "")) {
        _mqttClient.subscribe(device.getEraseTopicWild().c_str());
    }
    Serial.print(".");

    return _mqttClient.connected();
}

void MqttClient::checkAvailability() {
    if (!_mqttClient.connected()) {
        long now = millis();
        if (now - _lastReconnectAttempt > 3000) {
            _lastReconnectAttempt = now;
            if (reconnect()) {
                _lastReconnectAttempt = 0;
                if (device.getID() != -1) {
                    device.publishConnectMessage();
                } else {
                    device.publishCreateMessage();
                }
            }
        }
    } else {
        _mqttClient.loop();
    }
}

string MqttClient::getBrokerURL() {
    return _brokerURL;
}

void MqttClient::setBrokerURL(string brokerURL) {
    _brokerURL = brokerURL;
}

PubSubClient &MqttClient::getMQTT() {
    return _mqttClient;
}