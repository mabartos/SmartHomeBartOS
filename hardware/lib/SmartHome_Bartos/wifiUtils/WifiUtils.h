#ifndef WIFI_UTILS_H
#define WIFI_UTILS_H

#include <WiFiManager.h>

#include <string>

using namespace std;

class WifiUtils {
   private:
    char* _brokerURL;
    long _homeID;
    WiFiManager& _wifiManager;

    bool _shouldSaveConfig = false;

    void readSaved();
    void writeSaved();

    void setWifiManager();
    void saveConfigCallback();

    void reset();

   public:
    WifiUtils(WiFiManager& wifiManager);
    ~WifiUtils() = default;

    void begin();

    string getBrokerURL();
    long getHomeID();
    WiFiManager& getWifiManager();

    void setShouldSaveConfig(const bool& state);

    void shouldClearStates(const bool& state);
};

#endif