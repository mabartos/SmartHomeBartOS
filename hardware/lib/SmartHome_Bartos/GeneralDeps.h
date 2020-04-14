#ifndef GENERAL_DEPS_H
#define GENERAL_DEPS_H

#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#include <memory>

#include "capability/humidity/HumidityCap.h"
#include "capability/temperature/TemperatureCap.h"
#include "device/Device_deps.h"
#include "mqtt/MqttClient.h"

using namespace std;

#endif  // GENERAL_DEPS_H
