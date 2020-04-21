#ifndef WIFI_UTILS_H
#define WIFI_UTILS_H

#include <ArduinoJson.h>
#include <WiFiManager.h>

#include <string>

using namespace std;

class WifiUtils {
   private:
    char* _brokerURL;
    long _homeID;
    WiFiManager& _wifiManager;

    DynamicJsonDocument& _configDoc;

    bool _shouldSaveConfig = false;

    void readSaved();
    void writeSaved();

    void setWifiManager();
    void saveConfigCallback();

    void reset();

   public:
    WifiUtils(WiFiManager& wifiManager, DynamicJsonDocument& configDoc);
    ~WifiUtils() = default;

    void begin();

    string getBrokerURL();
    long getHomeID();
    WiFiManager& getWifiManager();

    bool shouldSaveConfig();

    void setShouldSaveConfig(const bool& state);

    void shouldClearStates(const bool& state);

    DynamicJsonDocument& getConfigDoc();
};

#endif