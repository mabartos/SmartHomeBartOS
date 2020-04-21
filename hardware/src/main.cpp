using namespace std;

#include "GeneralDeps.h"
#include "capability/humidity/HumidityCap.h"
#include "capability/lights/LightsCap.h"
#include "capability/temperature/TemperatureCap.h"
#include "credentials.h"
#include "mqtt/MessageForwarder.h"
#include "wifiUtils/WifiUtils.h"

DynamicJsonDocument configDocument(800);

WiFiClient espClient;
PubSubClient clientPub(espClient);
MqttClient client(clientPub);
WiFiManager wifiManager;
WifiUtils wifiUtils(wifiManager, configDocument);

// SENSORS
#define DHTTYPE DHT11
const char *CONFIG_FILE = "/config.json";

DHT dht(D5, DHTTYPE);

shared_ptr<TemperatureCap> temp = make_shared<TemperatureCap>(D5, dht);
shared_ptr<HumidityCap> hum = make_shared<HumidityCap>(D5, dht);
shared_ptr<LightsCap> light = make_shared<LightsCap>(D1);

Device device;
MessageForwarder forwarder;

void saveConfigCallback() {
    Serial.println("SAVE");
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

    wifiUtils.shouldClearStates(true);
    wifiManager.setSaveConfigCallback(saveConfigCallback);
    wifiUtils.begin();

    // DEBUG
    Serial.println("NAME");
    Serial.println(device.getName().c_str());
    Serial.println("ID");
    Serial.println(device.getID());
    Serial.println("BROKER");
    Serial.println(wifiUtils.getBrokerURL().c_str());

    Serial.println("HOME_ID");
    Serial.println(wifiUtils.getHomeID());
    Serial.println("ROOM_ID");
    Serial.println(device.getRoomID());

    //

    client.init(wifiUtils.getBrokerURL());
    device.setHomeID(wifiUtils.getHomeID());

    client.getMQTT().subscribe(device.getHomeTopicWildCard().c_str());
    client.getMQTT().setCallback(forwardMessages);

    // TODO automate
    device.addCapability(temp);
    device.addCapability(hum);
    device.addCapability(light);

    device.initAllCapabilities();

    if (wifiUtils.shouldSaveConfig()) {
        device.publishCreateMessage();
    } else {
        device.publishConnectMessage();
    }

    wifiUtils.setShouldSaveConfig(false);
}

void loop() {
    client.checkAvailability();
    device.executeAllCapabilities();
    delay(10);
}