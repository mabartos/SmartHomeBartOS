import React, {useState} from "react";
import {useObserver} from "mobx-react-lite";
import {SemipolarLoading} from 'react-loadingg';
import useStores from "../../hooks/useStores";
import {useParams} from "react-router-dom";
import {MqttService} from "../../services/mqtt/MqttService";
import DeviceDataCard from "../../components/DeviceData/DeviceDataCard";
import GridContainer from "../../components/Grid/GridContainer";

export default function Room() {
    const {authStore, deviceStore, homeStore} = useStores();
    const {homeID, roomID} = useParams();

    const [data, setData] = useState("");

    React.useEffect(() => {
        const mqtt = new MqttService("localhost", "userID", `/homes/${homeID}/rooms/${roomID}/#`, "sdsd");
        mqtt.client.onMessageArrived = (message) => setData(message);

        return function cleanup() {
            mqtt.disconnect();
        };
    }, []);

    React.useEffect(() => {
        deviceStore.setHomeID(homeID);
        deviceStore.setRoomID(roomID);
        deviceStore.getAllDevices();
    }, []);

    let caps = [];

    return useObserver(() => {
        const {isAuthenticated, user} = authStore;
        const {devices} = deviceStore;
        const {error} = homeStore;

        const setUpCapabilities = devices.forEach((value, index) => {
            value.capabilities.map(value => {
                caps.push(value);
            })
        });

        const getCapabilities = caps.map((value, index) => (
            <DeviceDataCard key={index} device={value} data={data} homeID={homeID} roomID={roomID}/>
        ));

        if (isAuthenticated) {
            return (
                <div>
                    Home ID={homeID}
                    Room ID={roomID}
                    Message: {data.payloadString}
                    ERR:{error}
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