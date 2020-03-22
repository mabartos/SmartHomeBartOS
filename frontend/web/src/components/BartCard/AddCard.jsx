import React from "react";
import {Clickable} from "react-clickable";
import AddBoxIcon from "@material-ui/icons/AddBox";
import {makeStyles} from "@material-ui/core/styles";
import GeneralInfoCard from "./GeneralInfoCard";

const useInfoStyle = makeStyles(style => ({
    container: {
        color: "primary",
        align: "center",
        textAlign: "center",
    },
    icon: {
        margin: "-15px",
        height: "200px",
        width: "100%"
    }
}));

export default function AddCard(props) {
    const infoClasses = useInfoStyle();

    const onSelect = () => {
    };

    return (
        <GeneralInfoCard color={props.color} title={props.title}>
            <Clickable onClick={onSelect}>
                <div className={infoClasses.container}>
                    <AddBoxIcon className={infoClasses.icon}/>
                </div>
            </Clickable>
        </GeneralInfoCard>
    );
}