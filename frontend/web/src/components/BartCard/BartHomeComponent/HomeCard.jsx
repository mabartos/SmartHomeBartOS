import React from "react";
import MainDisplayCard from "../MainDisplayCard";
import CardIcon from "components/Card/CardIcon.js";
import {HomeComponent} from "../../../index";
import {useHistory, useRouteMatch} from "react-router-dom";

export default function HomeCard(props) {
    const {path} = useRouteMatch();
    const history = useHistory();

    const {value, colorIndex} = props;

    const onSelect = () => {
        history.push(`${path}/${props.value.id}`);
    };

    return (
        <MainDisplayCard type={HomeComponent.HOME} homeID={value.id} title={value.name}
                         active={value.active} onSelect={onSelect} brokerURL={value.brokerURL}
                         home={value}
                         color={CardIcon.getColorID(colorIndex + 2)}/>
    );
}