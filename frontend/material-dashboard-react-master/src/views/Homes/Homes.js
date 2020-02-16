import React from "react";
import GridContainer from "components/Grid/GridContainer.js";

import HomeCard from "../../components/BartCard/HomeCard";
import image1 from "assets/img/sidebar-4.jpg";

export default function Homes() {
    return (
        <div>
            <GridContainer>

                <HomeCard title="Home 1" active message="Hey yooou" color="success"/>
                <HomeCard title="My Beautiful home 123" message="Hey yooou" color="danger" image={image1}/>
                <HomeCard title="Home 3" status="Active" message="Hey yooou" color="warning"/>
                <HomeCard title="Home 3" status="Active" message="Hey yooou" color="warning"/>
                <HomeCard title="Home 3" status="Active" message="Hey yooou" color="warning"/>
            </GridContainer>
        </div>
    );
}