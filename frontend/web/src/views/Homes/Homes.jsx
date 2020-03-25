import React, {useEffect} from "react";
import GridContainer from "components/Grid/GridContainer.js";
import AddCard from "../../components/BartCard/AddCard";
import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";
import NoItemsAvailable from "../../components/BartCard/NoItemsAvailable";
import {SemipolarLoading} from 'react-loadingg';
import ErrorNotification from "../../components/Notifications/ErrorNotification";
import SuccessNotification from "../../components/Notifications/SuccessNotification";
import HomeCard from "../../components/BartCard/BartHomeComponent/HomeCard";

export default function Homes() {
    const {homeStore, authStore} = useStores();

    useEffect(() => {
        authStore.initKeycloak();
    }, []);

    useEffect(() => {
        homeStore.getAllHomes();
        const interval = setInterval(() => {
            homeStore.reloadHomes();
        }, 2000);
        return () => clearInterval(interval);
    }, [homeStore]);

    return useObserver(() => {
        const {error, loading, actionInvoked, homes} = homeStore;
        const allHomes = [...homes].map(([key, item], index) => (
            <HomeCard key={index} value={item} colorIndex={index}/>
        ));
        const printAllHomes = [...homes].length === 0 ? <NoItemsAvailable message={"No Homes found"}/> : allHomes;

        return (
            <div>
                {error && <ErrorNotification message={error.message || "Error occurred."}/>}
                {actionInvoked && <SuccessNotification message={actionInvoked}/>}
                {loading && <SemipolarLoading/>}
                <GridContainer>
                    {printAllHomes}
                    <AddCard title="Add Home" color="success"/>
                </GridContainer>
            </div>
        );
    })

}