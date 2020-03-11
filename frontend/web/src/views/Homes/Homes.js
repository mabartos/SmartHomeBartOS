import React from "react";
import GridContainer from "components/Grid/GridContainer.js";

import HomeCard from "../../components/BartCard/HomeCard";
import AddCard from "../../components/BartCard/AddCard";
import {useObserver} from "mobx-react-lite"
import useStores from "../../hooks/useStores";

export default function Homes() {
    const {homeStore} = useStores();

    return useObserver(() => {
        const {error, loading, homes} = homeStore;
        const allHomes = (homes.map((item, index) => (
            <HomeCard key={index} title={item.name} active={item.mqttClient.brokerActive} color="info"/>
        )));

        return (
            <div>
                <GridContainer>
                    {allHomes}
                    <AddCard title="Add Home" color="success"/>
                </GridContainer>
            </div>
        );
    })

}