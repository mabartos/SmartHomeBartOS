import Paho from "paho-mqtt";
import { BROKER_URL_REGEX } from "../../index";
import * as React from "react";
import GeneralService from "../GeneralService";

export function MqttService(brokerURL, topic) {
    const regex = new RegExp(BROKER_URL_REGEX);
    const group = brokerURL.match(regex);
    const hostname = (group && group.length > 2) ? group[2] : brokerURL;
    let client;
    let shouldBeEnabled = true;

    function uuidv4() {
        return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }

    this.init = () => {
        client = new Paho.Client(hostname, Number(8000), uuidv4());

        client.onConnectionLost = onConnectionLost;
        client.onMessageArrived = onMessageArrived;

        client.connect({ onSuccess: onConnect });
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

    this.send = (topic, data) => {
        this.sendWithParams(topic, data, 1, false);
    };

    this.sendWithParams = (topic, data, qos, retained) => {
        if (typeof(data) === "object") {
            data = GeneralService.getStringFromObject(data);
            if (!data) return;
        }
        let message = new Paho.Message(data);
        message.destinationName = topic;
        message.qos = qos;
        message.retained = retained;
        client.send(message);
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