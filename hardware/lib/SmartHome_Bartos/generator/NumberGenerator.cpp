
#include "NumberGenerator.h"

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
unsigned NumberGenerator::generateInt(int min, int max) {
    srand((unsigned)time(NULL));
    double seed = rand() / (RAND_MAX + 1.);
    return seed * (max - min) + min;
}