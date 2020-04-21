#include "WifiUtils.h"

#include <ArduinoJson.h>
#include <ESP8266WebServer.h>
#include <ESP8266WiFi.h>
#include <FS.h>

#include "ESP8266TrueRandom.h"
#include "mqtt/MessageForwarder.h"
#include "mqtt/MqttClient.h"

#define BROKER_URL_SIZE 60
#define ID_SIZE 60
#define UUID_SIZE 50
#define NAME_SIZE 60

extern MqttClient client;
extern Device device;
extern const char *CONFIG_FILE;

char brokerURL[BROKER_URL_SIZE];
char homeID[ID_SIZE];
char UUID[UUID_SIZE];
char name[NAME_SIZE];
bool readActionProvided = false;

WifiUtils::WifiUtils(WiFiManager &wifiManager, DynamicJsonDocument &configDoc) : _wifiManager(wifiManager), _configDoc(configDoc) {
}

void WifiUtils::begin() {
    readSaved();
    setWifiManager();
    writeSaved();

    device.setHomeID(_homeID);
}

void WifiUtils::readSaved() {
    if (SPIFFS.begin() && SPIFFS.exists(CONFIG_FILE)) {
        File configFile = SPIFFS.open(CONFIG_FILE, "r");
        if (configFile) {
            size_t size = configFile.size();

            std::unique_ptr<char[]> buf(new char[size]);
            configFile.readBytes(buf.get(), size);

            DeserializationError err = deserializeJson(_configDoc, buf.get());
            if (err != DeserializationError::Ok)
                return;

            serializeJson(_configDoc, Serial);
            const JsonObject object = _configDoc.as<JsonObject>();

            vector<string> keys{"brokerURL",
                                "homeID",
                                "uuid",
                                "name"};
            if (MessageForwarder::containKeys(object, keys)) {
                strcpy(brokerURL, _configDoc["brokerURL"]);
                strcpy(homeID, _configDoc["homeID"]);
                strcpy(UUID, _configDoc["uuid"]);
                strcpy(name, _configDoc["name"]);

                _homeID = strtol(homeID, nullptr, 10);

                if (_configDoc.containsKey("roomID")) {
                    long roomID = _configDoc["roomID"];
                    device.setRoomID(roomID);
                }

                device.setName(string(name));
                client.setUUID(string(UUID));
                readActionProvided = true;
            }

            if (_configDoc.containsKey("deviceID")) {
                long devID = _configDoc["deviceID"];
                device.setID(devID);
            }

            configFile.close();
        }
    }
}

void WifiUtils::writeSaved() {
    if (_shouldSaveConfig) {
        //DynamicJsonDocument doc(BROKER_URL_SIZE + ID_SIZE + UUID_SIZE + 300);

        _configDoc["brokerURL"] = brokerURL;
        _configDoc["homeID"] = homeID;
        _configDoc["name"] = device.getName().c_str();

        // SAVE UUID
        byte uuidNumber[UUID_SIZE];
        ESP8266TrueRandom.uuid(uuidNumber);
        String uuidStr = ESP8266TrueRandom.uuidToString(uuidNumber);
        const char *uuid = uuidStr.c_str();
        _configDoc["uuid"] = uuid;
        strcpy(UUID, uuid);
        client.setUUID(string(uuid));

        File configFile = SPIFFS.open(CONFIG_FILE, "w");
        if (!configFile) {
            reset();
            return;
        }

        serializeJson(_configDoc, Serial);
        if (serializeJson(_configDoc, configFile) == 0) {
            reset();
            return;
        }

        configFile.close();
    }
}

void WifiUtils::setWifiManager() {
    string label("<h3> Device : " + device.getName() + "</h3>");
    WiFiManagerParameter label_parameter(label.c_str());
    WiFiManagerParameter brokerURL_parameter("broker", "MQTT Broker URL", brokerURL, BROKER_URL_SIZE);
    WiFiManagerParameter homeID_parameter("homeID", "Home ID", homeID, ID_SIZE);

    _wifiManager.addParameter(&label_parameter);
    _wifiManager.addParameter(&brokerURL_parameter);
    _wifiManager.addParameter(&homeID_parameter);

    string WIFI_NAME("SmartHome-" + device.getName());

    if (!_wifiManager.autoConnect(WIFI_NAME.c_str())) {
        reset();
    }

    if (!readActionProvided) {
        strcpy(brokerURL, brokerURL_parameter.getValue());
        strcpy(homeID, homeID_parameter.getValue());
        readActionProvided = false;
    }

    _brokerURL = brokerURL;
    _homeID = strtol(homeID, nullptr, 10);
}

string WifiUtils::getBrokerURL() {
    return string(_brokerURL);
}

long WifiUtils::getHomeID() {
    return _homeID;
}

WiFiManager &WifiUtils::getWifiManager() {
    return _wifiManager;
}

void WifiUtils::setShouldSaveConfig(const bool &state) {
    _shouldSaveConfig = state;
}

bool WifiUtils::shouldSaveConfig() {
    return _shouldSaveConfig;
}

void WifiUtils::reset() {
    Serial.println("Reset device");
    delay(3000);
    ESP.reset();
    delay(5000);
}

void WifiUtils::shouldClearStates(const bool &state) {
    if (state) {
        SPIFFS.format();
        _wifiManager.resetSettings();
    }
}

DynamicJsonDocument &WifiUtils::getConfigDoc() {
    return _configDoc;
}