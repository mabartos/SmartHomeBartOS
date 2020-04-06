import React, {useRef} from "react";
import {Clickable} from "react-clickable";
import AddBoxIcon from "@material-ui/icons/AddBox";
import {makeStyles} from "@material-ui/core/styles";
import GeneralInfoCard from "./GeneralInfoCard";
import {HomeComponent} from "../../index";
import {AddHomeForm} from "../Forms/Add/AddHomeForm";
import {AddRoomForm} from "../Forms/Add/AddRoomForm";

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
    const ref = useRef(null);

    const getForm = () => {
        switch (props.type) {
            case HomeComponent.HOME:
                return (<AddHomeForm ref={ref} {...props}/>);
            case HomeComponent.ROOM:
                return (<AddRoomForm ref={ref} {...props}/>);
            case HomeComponent.DEVICE:
                break;
        }
    };

    const onSelect = () => {
        ref.current.openForm();
    };

    return (
        <GeneralInfoCard hideFooter color={props.color} title={props.title}>
            {getForm()}
            <Clickable onClick={onSelect}>
                <div className={infoClasses.container}>
                    <AddBoxIcon className={infoClasses.icon}/>
                </div>
            </Clickable>
        </GeneralInfoCard>
    );
}