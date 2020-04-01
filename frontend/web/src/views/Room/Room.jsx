import React from "react";
import {useObserver} from "mobx-react-lite";
import {SemipolarLoading} from 'react-loadingg';
import useStores from "../../hooks/useStores";
import {useParams} from "react-router-dom";

export default function Room(props) {
    const {authStore, deviceStore} = useStores();
    const {homeID, roomID} = useParams();

    React.useEffect(() => {
        deviceStore.setHomeID(homeID);
        deviceStore.setRoomID(roomID);
        deviceStore.getAllDevices();
    }, []);

    return useObserver(() => {
        const {isAuthenticated} = authStore;
        const {devices} = deviceStore;

        //TODO
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