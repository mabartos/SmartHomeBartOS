#ifndef WIFI_UTILS_H
#define WIFI_UTILS_H

#include <ArduinoJson.h>
#include <WiFiManager.h>

#include <string>

using namespace std;

class WifiUtils {
   private:
    char* _brokerURL;
    char* _serverURL;
    long _homeID;
    WiFiManager& _wifiManager;

    bool _shouldSaveConfig = false;
    bool _alreadyCreated = false;

    void readSaved();
    void writeSaved();

    void setWifiManager();
    void saveConfigCallback();

   public:
    WifiUtils(WiFiManager& wifiManager);
    ~WifiUtils() = default;

    void begin();

    long getHomeID();
    WiFiManager& getWifiManager();

    bool shouldSaveConfig();

    void setShouldSaveConfig(const bool& state);

    void shouldClearStates(const bool& state);

    bool alreadyDeviceCreated();
    void reset();
};

#endif