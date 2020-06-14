
#include "NumberGenerator.h"

#include <time.h>

#include <sstream>

#include "ESP8266TrueRandom.h"

unsigned long NumberGenerator::generateLong(long min, long max) {
    return random(max - min + 1) + min;
}

string NumberGenerator::generateIntToString(int min, int max) {
    return intToString(generateLong(min, max));
}

string NumberGenerator::generateLongToString(long min, long max) {
    return longToString(generateLong(min, max));
}

string NumberGenerator::intToString(int number) {
    ostringstream stream;
    stream << number;
    return stream.str();
}

string NumberGenerator::longToString(long number) {
    ostringstream stream;
    stream << number;
    return stream.str();
}