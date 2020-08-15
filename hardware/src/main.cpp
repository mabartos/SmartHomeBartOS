using namespace std;
#include <SPI.h>

#include "GeneralDeps.h"
#include "capabilities.h"
#include "http/HttpClient.h"
#include "mqtt/MessageForwarder.h"
#include "wifiUtils/WifiUtils.h"

WiFiClient espClient;
PubSubClient clientPub(espClient);
MqttClient client(clientPub);
WiFiManager wifiManager;
WifiUtils wifiUtils(wifiManager);
HttpClient httpClient;

const char *CONFIG_FILE = "/config.json";

Device device;
MessageForwarder forwarder;

void saveConfigCallback() {
    wifiUtils.setShouldSaveConfig(true);
}

void forwardMessages(char *topic, byte *payload, unsigned int length) {
    DynamicJsonDocument doc(1024);
    DeserializationError err = deserializeJson(doc, payload, length);
    if (err) {
        Serial.println(err.c_str());
        return;
    }

    forwarder.forwardMessage(topic, doc);
    doc.garbageCollect();
}

void setup() {
    Serial.begin(9600);

    wifiUtils.shouldClearStates(shouldClearState);
    wifiManager.setSaveConfigCallback(saveConfigCallback);
    wifiUtils.begin();

    client.init(device.getBrokerURL());
    client.getMQTT().setCallback(forwardMessages);

    httpClient.setServerURL(device.getServerURL());

    device.setCapabilities(createdCaps);
    device.initAllCapabilities();

    if (!wifiUtils.alreadyDeviceCreated()) {
        device.publishCreateMessage();
        wifiUtils.setShouldSaveConfig(false);
    } else {
        device.publishConnectMessage();
    }
}

void loop() {
    client.checkAvailability();
    device.executeAllCapabilities();
    delay(10);
}