using namespace std;

#include "GeneralDeps.h"
#include "capability/humidity/HumidityCap.h"
#include "capability/lights/LightsCap.h"
#include "capability/temperature/TemperatureCap.h"
#include "credentials.h"
#include "mqtt/MessageForwarder.h"
#include "wifiUtils/WifiUtils.h"

WiFiClient espClient;
PubSubClient clientPub(espClient);
MqttClient client(SSID, PASS, BROKER_URL, clientPub);
WiFiManager wifiManager;
WifiUtils wifiUtils(wifiManager);

// SENSORS
#define DHTTYPE DHT11

DHT dht(D5, DHTTYPE);

shared_ptr<TemperatureCap> temp = make_shared<TemperatureCap>(D5, dht);
shared_ptr<HumidityCap> hum = make_shared<HumidityCap>(D5, dht);
shared_ptr<LightsCap> light = make_shared<LightsCap>(D1);

Device device;
MessageForwarder forwarder;

void saveConfigCallback() {
    wifiUtils.setShouldSaveConfig(true);
}

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

    wifiUtils.shouldClearStates(false);
    wifiManager.setSaveConfigCallback(saveConfigCallback);
    wifiUtils.begin();

    client.init();
    device.setHomeID(wifiUtils.getHomeID());

    client.getMQTT().subscribe(device.getHomeTopicWildCard().c_str());
    client.getMQTT().setCallback(forwardMessages);

    // TODO automate
    device.addCapability(temp);
    device.addCapability(hum);
    device.addCapability(light);

    device.initAllCapabilities();
    device.publishCreateMessage();
}

void loop() {
    client.checkAvailability();
    device.executeAllCapabilities();
    delay(10);
}