import React from "react";
import MainDisplayCard from "../MainDisplayCard";
import CardIcon from "components/Card/CardIcon.js";
import {HomeComponent} from "../../../index";
import {useHistory, useLocation, useParams, useRouteMatch} from "react-router-dom";

export default function RoomCard(props) {
    const match = useRouteMatch();
    const {homeID} = useParams();
    const history = useHistory();
    const location = useLocation();

    const onSelect = () => {

    };

    return (
        <MainDisplayCard type={HomeComponent.ROOM} roomID={props.value.id} homeID={props.value.homeID}
                         title={props.value.name}
                         onSelect={onSelect}
                         color={CardIcon.getColorID(props.colorIndex)}/>
    );

}