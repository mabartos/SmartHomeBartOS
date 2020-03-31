import React from "react";
import MainDisplayCard from "../MainDisplayCard";
import CardIcon from "components/Card/CardIcon.js";
import {HomeComponent} from "../../../index";
import {useHistory, useRouteMatch} from "react-router-dom";

export default function RoomCard(props) {
    const match = useRouteMatch();
    const history = useHistory();

    const onSelect = () => {
        history.push(match.url + "/rooms/" + props.value.id);
    };

    return (
        <MainDisplayCard type={HomeComponent.ROOM} roomID={props.value.id} homeID={props.value.homeID}
                         title={props.value.name}
                         onSelect={onSelect}
                         color={CardIcon.getColorID(props.colorIndex)}/>
    );

}