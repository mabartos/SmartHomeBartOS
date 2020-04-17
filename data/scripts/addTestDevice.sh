#!/bin/bash

if [ $# -gt 1 ]; then
	mosquitto_pub -p 1883 -t "homes/$1/create" -f $2
elif [ $# -gt 0 ]; then
	mosquitto_pub -p 1883 -t "homes/$1/create" -f ../createDevice.json
else
	echo "You have to provide homeID !!"
fi