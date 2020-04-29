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
import {toJS} from "mobx";
import Sortable from "../../components/Sortable/Sortable";
import RoomCard from "../../components/BartCard/BartHomeComponent/RoomCard";
import NoItemsAvailable from "../../components/BartCard/NoItemsAvailable";

export default function Room() {
    const {authStore, deviceStore, homeStore} = useStores();
    const {homeID, roomID} = useParams();

    const [data, setData] = useState("");
    const [mqtt, setMqtt] = useState(null);

    React.useEffect(() => {
        let home = toJS(homeStore.homes);
        let brokerURL = home[homeID].brokerURL;
        if (!brokerURL)
            return;
        const mqttClient = new MqttService(brokerURL, `homes/${homeID}/rooms/${roomID}/#`);
        mqttClient.client.onMessageArrived = (message) => {
            setData(message);
        };
        setMqtt(mqttClient);

        return function cleanup() {
            mqttClient.disconnect();
        };
    }, []);

    React.useEffect(() => {
        deviceStore.deleteCapabilities();
        deviceStore.setHomeID(homeID);
        deviceStore.setRoomID(roomID);
        deviceStore.getAllDevices();

        return () => deviceStore.deleteCapabilities();
    }, []);

    useEffect(() => {
        deviceStore.getAllDevices();
        const interval = setInterval(() => {
            deviceStore.reloadDevices();
        }, 2000);
        return () => clearInterval(interval);
    }, [deviceStore]);

    return useObserver(() => {
        const {isAuthenticated} = authStore;
        const {error, actionInvoked, loading, capabilities, devices} = deviceStore;

        const getCapabilities = [...capabilities].map(([key, value], index) => (
                <DeviceDataCard key={index} capability={value} data={data} homeID={homeID} roomID={roomID}
                                devices={devices} notification={`Device '${value.name}'`}
                                color={CardIcon.getColorID(value.deviceID)}
                                mqtt={mqtt}
                />
            )
        );

        const printAllCaps = (capabilities.size === 0) ?
            <NoItemsAvailable message={"No Devices found"}/> : getCapabilities;

        if (isAuthenticated) {
            return (
                <div>
                    {error && <ErrorNotification message={error.message || "Error occurred"}/>}
                    {actionInvoked && <SuccessNotification message={actionInvoked}/>}
                    {loading && <SemipolarLoading/>}


                    <GridContainer>
                        {printAllCaps}
                    </GridContainer>
                </div>
            )
        } else {
            return (<SemipolarLoading/>)
        }

    });
}