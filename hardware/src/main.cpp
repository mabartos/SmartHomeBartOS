#include "GeneralDeps.h"
#include "credentials.h"

WiFiClient espClient;
PubSubClient clientPub(espClient);
MqttClient client(SSID, PASS, BROKER_URL, clientPub);

shared_ptr<TemperatureCap> temp = make_shared<TemperatureCap>(10);
shared_ptr<HumidityCap> hum = make_shared<HumidityCap>(11);

Device device;

void forwardMessages(char *topic, byte *payload, unsigned int length) {
    Serial.print("Message arrived [");
    Serial.print(topic);
    Serial.print("] ");
    for (unsigned i = 0; i < length; i++) {
        Serial.print((char)payload[i]);
    }
    Serial.println();
}

void setup() {
    Serial.begin(9600);
    client.init();
    client.getMQTT().setCallback(forwardMessages);

    device.addCapability(temp);
    device.addCapability(hum);

    device.initAllCapabilities();
}

void loop() {
    client.checkAvailability();
    device.executeAllCapabilities();
}