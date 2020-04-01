import React from "react";
import {useObserver} from "mobx-react-lite";
import {SemipolarLoading} from 'react-loadingg';
import useStores from "../../hooks/useStores";
import {useParams} from "react-router-dom";
import Paho from "paho-mqtt";
import MqttService from "../../services/mqtt/MqttService";

const client = () => {
    const client = new Paho.Client("0.0.0.0", Number(8000), "testClient");

// set callback handlers
    client.onConnectionLost = onConnectionLost;
    client.onMessageArrived = onMessageArrived;

// connect the client
    client.connect({onSuccess: onConnect});

// called when the client connects
    function onConnect() {
        // Once a connection has been made, make a subscription and send a message.
        console.log("onConnect");
        client.subscribe("World");
        let message = new Paho.Message("Hello");
        message.destinationName = "World";
        client.send(message);
    }

// called when the client loses its connection
    function onConnectionLost(responseObject) {
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }

// called when a message arrives
    function onMessageArrived(message) {
        console.log("onMessageArrived:" + message.payloadString);
    }

};

export default function Room(props) {
    const {authStore, deviceStore, homeStore} = useStores();
    const {homeID, roomID} = useParams();

    React.useEffect(() => {
        //client();
        const client = new MqttService("tcp://localhost:8080", "clientTest");
    }, []);// Create a client instance


    React.useEffect(() => {
        deviceStore.setHomeID(homeID);
        deviceStore.setRoomID(roomID);
        deviceStore.getAllDevices();
    }, []);
    let home;

    return useObserver(() => {
        const {isAuthenticated, user} = authStore;
        const {devices} = deviceStore;
        const {homes} = homeStore;

        devices.forEach((value, index) => {
            value.capabilities.forEach((value) => {
                console.log(value);
                console.log(value.name);
                console.log(value.enabled);
            });

        });

        if (isAuthenticated) {
            return (
                <div>
                    Room ID={roomID}
                </div>
            )
        } else {
            return (<SemipolarLoading/>)
        }

    });
}