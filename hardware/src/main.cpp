using namespace std;

#include "GeneralDeps.h"
#include "capability/humidity/HumidityCap.h"
#include "capability/temperature/TemperatureCap.h"
#include "credentials.h"
#include "mqtt/MessageForwarder.h"

WiFiClient espClient;
PubSubClient clientPub(espClient);
MqttClient client(SSID, PASS, BROKER_URL, clientPub);

shared_ptr<TemperatureCap> temp = make_shared<TemperatureCap>(10);
shared_ptr<HumidityCap> hum = make_shared<HumidityCap>(11);

Device device;
MessageForwarder forwarder;

void forwardMessages(char *topic, byte *payload, unsigned int length) {
    DynamicJsonDocument doc(length + length / 3);
    DeserializationError err = deserializeJson(doc, payload, length);
    if (err != DeserializationError::Ok)
        return;

    forwarder.forwardMessage(topic, doc);
    doc.garbageCollect();
}

void setup() {
    Serial.begin(9600);
    client.init();
    // TODO WifiManager
    device.setHomeID(22);

    client.getMQTT().subscribe(device.getHomeTopicWildCard().c_str());
    client.getMQTT().setCallback(forwardMessages);

    // TODO automate
    device.addCapability(temp);
    device.addCapability(hum);

    device.initAllCapabilities();
    device.publishCreateMessage();
}

void loop() {
    client.checkAvailability();
    device.executeAllCapabilities();
    delay(10);
}