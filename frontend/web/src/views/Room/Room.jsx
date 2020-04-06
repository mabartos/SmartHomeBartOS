import React, {useEffect, useState} from "react";
import {useObserver} from "mobx-react-lite";
import {SemipolarLoading} from 'react-loadingg';
import useStores from "../../hooks/useStores";
import {useParams} from "react-router-dom";
import {MqttService} from "../../services/mqtt/MqttService";
import DeviceDataCard from "../../components/DeviceData/DeviceDataCard";
import CardIcon from "../../components/Card/CardIcon.js";
import GridContainer from "../../components/Grid/GridContainer";
import ErrorNotification from "../../components/Notifications/ErrorNotification";
import SuccessNotification from "../../components/Notifications/SuccessNotification";

export default function Room() {
    const {authStore, deviceStore, homeStore} = useStores();
    const {homeID, roomID} = useParams();

    const [data, setData] = useState("");
    const [mqtt, setMqtt] = useState(null);

    React.useEffect(() => {
        const mqttClient = new MqttService("localhost", `/homes/${homeID}/rooms/${roomID}/#`);
        mqttClient.client.onMessageArrived = (message) => setData(message);
        setMqtt(mqttClient);

        return function cleanup() {
            mqttClient.disconnect();
        };
    }, []);

    React.useEffect(() => {
        deviceStore.setHomeID(homeID);
        deviceStore.setRoomID(roomID);
        deviceStore.getAllDevices();
    }, []);

    useEffect(() => {
        deviceStore.getAllDevices();
        const interval = setInterval(() => {
            deviceStore.reloadDevices();
        }, 2000);
        return () => clearInterval(interval);
    }, [deviceStore]);

    let caps = [];

    return useObserver(() => {
        const {isAuthenticated, user} = authStore;
        const {devices, error, actionInvoked, loading} = deviceStore;

        const setUpCapabilities = devices.forEach((value, index) => {
            value.capabilities.map(cap => {
                cap.deviceName = value.name;
                caps.push(cap);
            })
        });

        const getCapabilities = caps.map((value, index) => (
                <DeviceDataCard key={index} device={value} data={data} homeID={homeID} roomID={roomID}
                                notification={`Device '${value.deviceName}'`}
                                color={CardIcon.getColorID(value.deviceID)}
                                mqtt={mqtt}
                />
            )
        );

        if (isAuthenticated) {
            return (
                <div>
                    {error && <ErrorNotification message={error.message || "Error occurred"}/>}
                    {actionInvoked && <SuccessNotification message={actionInvoked}/>}
                    {loading && <SemipolarLoading/>}

                    Home ID={homeID}
                    Room ID={roomID}
                    Message: {data.payloadString}
                    ERR:{}
                    <GridContainer>
                        {getCapabilities}
                    </GridContainer>
                </div>
            )
        } else {
            return (<SemipolarLoading/>)
        }

    });
}