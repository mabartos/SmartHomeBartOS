#!/bin/bash

if [ $# -gt 3  ]; then
	mosquitto_pub -p 1883 -t "/homes/$1/rooms/$2/temp/$3" -f $4
elif [ $# -gt 2 ]; then
	mosquitto_pub -p 1883 -t "/homes/$1/rooms/$2/temp/$3" -f ../sendTemperature.json
else
	echo "Wrong args !!"
fi
