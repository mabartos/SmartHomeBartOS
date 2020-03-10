import React from "react";
import GridContainer from "components/Grid/GridContainer.js";

import HomeCard from "../../components/BartCard/HomeCard";
import image1 from "assets/img/sidebar-4.jpg";
import AddCard from "../../components/BartCard/AddCard";
import {useObserver} from "mobx-react-lite"
import useStores from "../../hooks/useStores";

export default function Homes() {
    const {homeStore} = useStores();

    return useObserver(() => {
        //homeStore.getAllHomes();
        const {error, loading, homes} = homeStore;
        const all = (homes.map((home,index) =>{
            console.log(home);
        }

            ));

        return (
            <div>
                <GridContainer>
                    {all}
                    <HomeCard title="Home 1" active color="success"/>
                    <HomeCard title="My Beautiful home 1234567  " color="danger" image={image1}/>
                    <HomeCard title="Home 2" color="info"/>
                    <HomeCard title="Home 4" active color="warning"/>
                    <HomeCard title="Home 5" color="primary"/>
                    <AddCard title="Add Home" color="success"/>
                </GridContainer>
            </div>
        );
    })

}