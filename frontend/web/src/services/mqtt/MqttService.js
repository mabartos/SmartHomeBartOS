import Paho from "paho-mqtt";
import {BROKER_URL_REGEX} from "../../index";
import * as React from "react";

export function MqttService(brokerURL, userID, topic) {
    const regex = new RegExp(BROKER_URL_REGEX);
    const group = brokerURL.match(regex);
    const hostname = (group && group.length > 2) ? group[2] : brokerURL;
    let client;
    let shouldBeEnabled = true;

    this.init = () => {
        client = new Paho.Client(hostname, Number(8000), userID);

        client.onConnectionLost = onConnectionLost;
        client.onMessageArrived = onMessageArrived;

        client.connect({onSuccess: onConnect});
    };

    function onConnect() {
        console.log("Connected MQTT over WS");
        client.subscribe(topic);
        shouldBeEnabled = true;
    }

    function onConnectionLost(responseObject) {
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }

    function onMessageArrived(message) {
        console.log("onMessageArrived:" + message.payloadString);
    }

    this.disconnect = () => {
        if (client && client.isConnected) {
            client.disconnect();
            shouldBeEnabled = false;
        }
    };

    this.init();

    this.client = client;

    setInterval(() => {
        if (client && !client.isConnected() && shouldBeEnabled) {
            this.init();
        }
    }, 3000);

    return this;
}