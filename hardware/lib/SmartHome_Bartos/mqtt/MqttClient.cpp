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
    if (_mqttClient.connect(getUUID().c_str())) {
        _mqttClient.subscribe(device.getCreateTopic().c_str());
        _mqttClient.subscribe(device.getCreateTopicResp().c_str());
    }
    return _mqttClient.connected();
}

void MqttClient::checkAvailability() {
    if (!_mqttClient.connected()) {
        long now = millis();
        if (now - _lastReconnectAttempt > 5000) {
            _lastReconnectAttempt = now;
            if (reconnect()) {
                _lastReconnectAttempt = 0;
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