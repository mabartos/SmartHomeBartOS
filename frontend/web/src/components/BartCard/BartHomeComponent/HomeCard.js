import React from "react";
import MainDisplayCard from "../MainDisplayCard";
import CardIcon from "components/Card/CardIcon.js";
import {HomeComponent} from "../../../index";
import {useHistory, useRouteMatch} from "react-router-dom";

export default function HomeCard(props) {
    const {path} = useRouteMatch();
    const history = useHistory();

    const onSelect = () => {
        history.push(`${path}/${props.value.id}`);
    };

    return (
        <MainDisplayCard type={HomeComponent.HOME} homeID={props.value.id} title={props.value.name}
                         active={props.value.active} onSelect={onSelect} brokerURL={props.value.brokerURL}
                         color={CardIcon.getColorID(props.colorIndex + 2)}/>
    );

}