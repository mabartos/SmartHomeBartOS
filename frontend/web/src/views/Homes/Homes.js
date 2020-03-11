import React from "react";
import GridContainer from "components/Grid/GridContainer.js";

import HomeCard from "../../components/BartCard/HomeCard";
import AddCard from "../../components/BartCard/AddCard";
import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";

export default function Homes() {
    const {homeStore} = useStores();

    return useObserver(() => {
        const {error, loading, homes} = homeStore;
        /*console.log(homes);*/

        const allHomes = [...homes].map(([key, item], index) => (
            <HomeCard key={index} id={item.id} title={item.name} active={item.mqttClient.brokerActive} color="info"/>
        ));

        return (
            <div>
                <GridContainer>
                    {allHomes}
                    <HomeCard title="sd" id={2}/>
                    <AddCard title="Add Home" color="success"/>
                </GridContainer>
            </div>
        );
    })

}