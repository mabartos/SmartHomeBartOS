import React from "react";
import GridContainer from "components/Grid/GridContainer.js";

import MainDisplayCard from "../../components/BartCard/MainDisplayCard";
import AddCard from "../../components/BartCard/AddCard";
import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";
import CardIcon from "components/Card/CardIcon.js";
import NoItemsAvailable from "../../components/BartCard/NoItemsAvailable";

export default function Homes() {
    const {homeStore} = useStores();

    return useObserver(() => {
        const {error, loading, homes} = homeStore;
        const allHomes = [...homes].map(([key, item], index) => (
            <MainDisplayCard key={index} id={item.id} title={item.name} active={item.mqttClient.brokerActive}
                             color={CardIcon.getColorID(index)}/>
        ));
        const printAllHomes = homes.length === 0 ? <NoItemsAvailable message={"No Homes found"}/> : allHomes;

        return (
            <div>
                <GridContainer>
                    {printAllHomes}
                    <AddCard title="Add Home" color="success"/>
                </GridContainer>
            </div>
        );
    })

}