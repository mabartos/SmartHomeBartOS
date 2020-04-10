import React, {useEffect} from "react";
import GridContainer from "components/Grid/GridContainer.js";
import AddCard from "../../components/BartCard/AddCard";
import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";
import NoItemsAvailable from "../../components/BartCard/NoItemsAvailable";
import {SemipolarLoading} from 'react-loadingg';
import HomeCard from "../../components/BartCard/BartHomeComponent/HomeCard";
import {HomeComponent} from "../../index";
import ErrorNotification from "../../components/Notifications/ErrorNotification";
import SuccessNotification from "../../components/Notifications/SuccessNotification";

export default function Homes() {
    const {homeStore, authStore} = useStores();

    useEffect(() => {
        authStore.initKeycloak();
    }, []);

    useEffect(() => {
        homeStore.getAllHomes();
    }, [homeStore]);

    return useObserver(() => {
        const {error, loading, actionInvoked, homes} = homeStore;
        const {isAuthenticated} = authStore;

        const allHomes = [...homes].map(([key, item], index) => (
            <HomeCard key={index} value={item} colorIndex={index}/>
        ));
        const printAllHomes = [...homes].length === 0 ? <NoItemsAvailable message={"No Homes found"}/> : allHomes;

        if (isAuthenticated) {
            return (
                <div>
                    {error && <ErrorNotification message={error.message || "Error occurred."}/>}
                    {actionInvoked && <SuccessNotification message={actionInvoked}/>}
                    {loading && <SemipolarLoading/>}
                    <GridContainer>
                        {printAllHomes}
                        <AddCard type={HomeComponent.HOME} title="Add Home" color="success"/>
                    </GridContainer>
                </div>
            );
        } else {
            return (<SemipolarLoading/>)
        }
    })

}