import React from "react";
import {useParams} from "react-router-dom";
import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";
import GridContainer from "../../components/Grid/GridContainer";
import NoItemsAvailable from "../../components/BartCard/NoItemsAvailable";
import AddCard from "../../components/BartCard/AddCard";
import {SemipolarLoading} from 'react-loadingg';
import RoomCard from "../../components/BartCard/BartHomeComponent/RoomCard";
import {HomeComponent} from "../../index";
import ErrorNotification from "../../components/Notifications/ErrorNotification";
import SuccessNotification from "../../components/Notifications/SuccessNotification";

export default function Home(props) {
    const {homeStore, roomStore, authStore, deviceStore} = useStores();
    const {homeID} = useParams();
    const id = parseInt(homeID || -1);

    React.useEffect(() => {
        if (!homeStore.homes[id]) {
            homeStore.getHomeByID(id);
            roomStore.reloadAllRooms(id);
        }
    }, [homeStore, roomStore, id]);

    React.useEffect(() => {
        authStore.initKeycloak();
        const interval = setInterval(() => {
            roomStore.reloadAllRooms(id);
        }, 2000);
        return () => clearInterval(interval);
    }, []);

    return useObserver(() => {
        const {isAuthenticated} = authStore;
        const {error, loading, rooms, actionInvoked} = roomStore;

        const getError = () => {
            if (error) {
                return error;
            } else if (homeStore.error) {
                return homeStore.error;
            } else if (roomStore.error) {
                return roomStore.error;
            } else if (deviceStore.error) {
                return deviceStore.error;
            }
        };

        const getActionInvoked = () => {
            if (actionInvoked) {
                return actionInvoked;
            } else if (homeStore.actionInvoked) {
                return homeStore.actionInvoked;
            } else if (roomStore.actionInvoked) {
                return roomStore.actionInvoked;
            } else if (deviceStore.actionInvoked) {
                return deviceStore.actionInvoked;
            }
        };

        const isLoading = () => {
            return loading || homeStore.loading || roomStore.loading || deviceStore.loading;
        };

        const filterRooms = (rooms, idHome) => {
            let tmp = new Map();
            [...rooms].map(([key, item], index) => {
                if (item.homeID === idHome) {
                    tmp.set(key, item);
                }
            });
            return tmp;
        };

        const allRooms = [...filterRooms(rooms, id)]
            .map(([key, item], index) => (
                <RoomCard key={index} value={item} colorIndex={index + 3}/>
            ));

        const printAllRooms = allRooms.length === 0 ? <NoItemsAvailable message={"No Rooms found"}/> : allRooms;

        if (isAuthenticated) {
            return (
                <div>
                    {getError() && <ErrorNotification message={getError() || "Error occurred"}/>}
                    {getActionInvoked() && <SuccessNotification message={getActionInvoked()}/>}
                    {isLoading() && <SemipolarLoading/>}
                    <GridContainer>
                        {printAllRooms}
                        <AddCard type={HomeComponent.ROOM} title="Add Room" color="success"/>
                    </GridContainer>
                </div>
            );
        } else {
            return (<SemipolarLoading/>)
        }
    });

}