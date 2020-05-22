#ifndef CAPABILITIES_H
#define CAPABILITIES_H

#include <Adafruit_Sensor.h>
#include <DHT.h>

#include <memory>
#include <string>
#include <vector>

#include "capability/Capability.h"
#include "capability/humidity/HumidityCap.h"
#include "capability/lights/LightsCap.h"
#include "capability/pir/PIRCap.h"
#include "capability/relay/RelayCap.h"
#include "capability/temperature/TemperatureCap.h"

using namespace std;

// SENSORS
#define DHTTYPE DHT11
DHT dht(D7, DHTTYPE);

bool shouldClearState = false;

vector<shared_ptr<Capability>> createdCaps{
    make_shared<HumidityCap>(D7, dht),
    make_shared<TemperatureCap>(D7, dht),
    make_shared<LightsCap>(D6),
    make_shared<LightsCap>(D5),
    make_shared<RelayCap>(D1),
    make_shared<RelayCap>(D2)};

#endif