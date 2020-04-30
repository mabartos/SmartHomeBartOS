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
DHT dht(D5, DHTTYPE);

bool shouldClearState = true;

vector<shared_ptr<Capability>> createdCaps{
    make_shared<HumidityCap>(D5, dht),
    make_shared<TemperatureCap>(D5, dht),
    make_shared<LightsCap>(D1),
    make_shared<RelayCap>(D2),
    make_shared<RelayCap>(D3),
    make_shared<PIRCap>(D6)};

#endif