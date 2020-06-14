#ifndef CAPABILITIES_H
#define CAPABILITIES_H

#include <Adafruit_Sensor.h>
#include <DHT.h>

#include <memory>
#include <string>
#include <vector>

#include "capability/general/Capability.h"
#include "core/HumidityCap.h"
#include "core/LightsCap.h"
#include "core/PIRCap.h"
#include "core/RelayCap.h"
#include "core/TemperatureCap.h"

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

/*vector<shared_ptr<Capability>> createdCaps{
    make_shared<RelayCap>(0)};
    */

#endif