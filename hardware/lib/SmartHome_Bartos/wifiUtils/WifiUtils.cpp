#include "WifiUtils.h"

#include <ArduinoJson.h>
#include <ESP8266WebServer.h>
#include <ESP8266WiFi.h>
#include <FS.h>

#define BROKER_URL_SIZE 60
#define HOME_ID_SIZE 50

const char *CONFIG_FILE = "/config.json";
const char *WIFI_NAME = "SmartHome Bartos";

char brokerURL[BROKER_URL_SIZE];
char homeID[HOME_ID_SIZE];

WifiUtils::WifiUtils(WiFiManager &wifiManager) : _wifiManager(wifiManager) {
}

void WifiUtils::begin() {
    readSaved();
    setWifiManager();

    _brokerURL = brokerURL;
    _homeID = atol(homeID);
}

void WifiUtils::readSaved() {
    if (SPIFFS.begin() && SPIFFS.exists(CONFIG_FILE)) {
        File configFile = SPIFFS.open(CONFIG_FILE, "r");
        if (configFile) {
            size_t size = configFile.size();

            std::unique_ptr<char[]> buf(new char[size]);
            configFile.readBytes(buf.get(), size);

            DynamicJsonDocument doc(5 * size);

            DeserializationError err = deserializeJson(doc, buf.get(), size);
            if (err != DeserializationError::Ok)
                return;

            serializeJson(doc, Serial);
            if (doc.containsKey("brokerURL") && doc.containsKey("homeID")) {
                strcpy(brokerURL, doc["brokerURL"]);
                strcpy(homeID, doc["homeID"]);
            }

            configFile.close();
        }
    }
}

void WifiUtils::writeSaved() {
    if (_shouldSaveConfig) {
        DynamicJsonDocument doc(BROKER_URL_SIZE + HOME_ID_SIZE + 100);
        doc["brokerURL"] = brokerURL;
        doc["homeID"] = homeID;

        File configFile = SPIFFS.open(CONFIG_FILE, "w");
        if (!configFile) {
            reset();
            return;
        }

        serializeJson(doc, Serial);
        if (serializeJson(doc, configFile) == 0) {
            reset();
            return;
        }

        configFile.close();
    }
}

void WifiUtils::setWifiManager() {
    WiFiManagerParameter brokerURL_parameter("broker", "MQTT Broker URL", brokerURL, BROKER_URL_SIZE);
    WiFiManagerParameter homeID_parameter("homeID", "Home ID", homeID, HOME_ID_SIZE);

    _wifiManager.addParameter(&brokerURL_parameter);
    _wifiManager.addParameter(&homeID_parameter);

    if (!_wifiManager.autoConnect(WIFI_NAME)) {
        reset();
    }

    strcpy(brokerURL, brokerURL_parameter.getValue());
    strcpy(homeID, homeID_parameter.getValue());
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