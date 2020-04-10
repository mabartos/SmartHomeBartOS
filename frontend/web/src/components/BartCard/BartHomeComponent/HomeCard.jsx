import React from "react";
import MainDisplayCard from "../MainDisplayCard";
import CardIcon from "components/Card/CardIcon.js";
import {HomeComponent} from "../../../index";
import {useHistory, useRouteMatch} from "react-router-dom";
import useStores from "../../../hooks/useStores";
import {useObserver} from "mobx-react-lite";
import {getRoleName} from "../../../constants/Role";

export default function HomeCard(props) {
    const {path} = useRouteMatch();
    const history = useHistory();
    const {homeStore} = useStores();

    const {value, colorIndex} = props;

    const onSelect = () => {
        history.push(`${path}/${props.value.id}`);
    };

    return useObserver(() => {
        const {rolesInHome} = homeStore;
        return (
            <MainDisplayCard type={HomeComponent.HOME} homeID={value.id} title={value.name}
                             active={value.active} onSelect={onSelect} brokerURL={value.brokerURL}
                             home={value} notification={getRoleName(rolesInHome.get(value.id) || "Role")}
                             color={CardIcon.getColorID(colorIndex + 2)} {...props}/>
        )
    });
}