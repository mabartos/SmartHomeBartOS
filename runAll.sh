#!/bin/bash

gnome-terminal --tab --title="Keycloak" -e "bash -c \"./scripts/runKeycloak.sh;exec bash\"" \
		--tab --title="Backend" -e "bash -c \"cd backend/dist/target;java -jar smarthome-dist-1.0.0-SNAPSHOT-runner.jar;exec bash\"" \
		--tab --title="Frontend" -e "bash -c \"cd frontend/web;yarn install; yarn start;exec bash\"" \
		 --tab --title="HiveMQ" -e "bash -c \"./scripts/runHiveMQ.sh;exec bash\"" &
exit


			
