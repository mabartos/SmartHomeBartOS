#ifndef NUMBER_GENERATOR_H
#define NUMBER_GENERATOR_H

#include <string>
using namespace std;

class NumberGenerator {
   public:
    static unsigned long generateLong(long min, long max);
    static string generateIntToString(int min, int max);
    static string generateLongToString(long min, long max);

    static string intToString(int number);
    static string longToString(long number);
};

#endif  // NUMBER_GENERATOR_H