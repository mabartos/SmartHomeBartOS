#!/bin/bash

gnome-terminal --tab --title="Keycloak" -e "bash -c \"./scripts/runKeycloak.sh;exec bash\"" \
		--tab --title="Frontend" -e "bash -c \"cd frontend/web;yarn start;exec bash\"" &

sleep 10

gnome-terminal --tab --title="Backend" -e "bash -c \"cd backend;mvn compile quarkus:dev;exec bash\""\
		--tab --title="HiveMQ" -e "bash -c \"./scripts/runHiveMQ.sh;exec bash\"" &

exit

			
