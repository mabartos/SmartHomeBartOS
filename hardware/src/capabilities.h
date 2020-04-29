#include <Adafruit_Sensor.h>
#include <DHT.h>

#include <memory>
#include <string>
#include <vector>

#include "capability/Capability.h"
#include "capability/humidity/HumidityCap.h"
#include "capability/lights/LightsCap.h"
#include "capability/temperature/TemperatureCap.h"

using namespace std;

// SENSORS
#define DHTTYPE DHT11
DHT dht(D5, DHTTYPE);

vector<shared_ptr<Capability>> createdCaps{
    make_shared<HumidityCap>(D5, dht),
    make_shared<TemperatureCap>(D5, dht),
    make_shared<LightsCap>(D1)};