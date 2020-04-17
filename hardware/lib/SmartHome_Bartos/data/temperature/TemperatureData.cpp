#include "TemperatureData.h"

TemperatureData::TemperatureData(const long &id, const string &name) : CapabilityData(id, name) {
}

double TemperatureData::getActualTemp() {
    return _actual;
}

void TemperatureData::setActualTemp(const double &actual) {
    _actual = actual;
}

DynamicJsonDocument TemperatureData::toJSON() {
    DynamicJsonDocument doc = getJSON();
    doc["actual"] = getActualTemp();
    return doc;
}