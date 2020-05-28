#!/bin/bash

if [ -z "$JAVA_HOME" ] 
then 
    echo "You have to define 'JAVA_HOME' variable. Required Java 11!"
    exit
fi

YARN=`which yarn`

if [ -z "$YARN" ]; then
    DEB=/etc/debian_version
    if [ -f "$DEB" ]; then
        curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
        echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list 
        sudo apt update && sudo apt install yarn
    else
        curl --silent --location https://dl.yarnpkg.com/rpm/yarn.repo | sudo tee /etc/yum.repos.d/yarn.repo && sudo dnf install yarn;
    fi
fi

cd frontend/web/ && yarn install;
cd ../../backend && ./mvnw package -DskipTests;

echo "----------------------"
echo "Installation completed"
echo "----------------------"
echo "Execute: ./runAll.sh"
echo "----------------------"
