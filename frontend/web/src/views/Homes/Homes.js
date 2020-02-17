import React from "react";
import GridContainer from "components/Grid/GridContainer.js";

import HomeCard from "../../components/BartCard/HomeCard";
import image1 from "assets/img/sidebar-4.jpg";
import AddCard from "../../components/BartCard/AddCard";
import {storesContext} from "../";

export default function Homes() {
    const homeStore = React.useContext(storesContext);

    return (
        <div>
            <GridContainer>
                <HomeCard title="Home 1" active color="success"/>
                <HomeCard title="My Beautiful home 1234567  " color="danger" image={image1}/>
                <HomeCard title="Home 2" color="info"/>
                <HomeCard title="Home 4" active color="warning"/>
                <HomeCard title="Home 5" color="primary"/>
                <AddCard title="Add Home" color="success"/>

            </GridContainer>
        </div>
    );
}