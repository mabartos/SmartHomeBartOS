import React, {useRef} from "react";
import GridItem from "components/Grid/GridItem.js";
import Card from "components/Card/Card.js";
import {makeStyles} from "@material-ui/core/styles";
import CardHeader from "components/Card/CardHeader.js";
import CardBody from "components/Card/CardBody.js";
import {whiteColor} from "../../assets/jss/material-dashboard-react";
import BartGeneralFooterCard from "./BartGeneralFooterCard";
import {BooleanDialog} from "../BartDialogs/BooleanDialog";
import {HomeComponent} from "../../index";
import {UpdateCapabilityForm} from "../Forms/Edit/UpdateCapabilityForm";
import useStores from "../../hooks/useStores";

const useInfoStyle = makeStyles(style => ({
    container: {
        color: "primary",
        align: "center",
        textAlign: "center",
    },
    general:{
      minWidth:"200px",
      minHeight:"200px",
    },
    title: {
        textAlign: "center",
        color: whiteColor,
        marginTop: "0px",
        minHeight: "auto",
        fontSize: "23px",
        fontWeight: "350",
        fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
        marginBottom: "3px",
        textDecoration: "none"
    },
    icon: {
        margin: "-15px",
        height: "200px",
        width: "100%"
    }
}));

export default function GeneralInfoCard(props) {
    const infoClasses = useInfoStyle();
    const refDelete = useRef(null);
    const refEdit = useRef(null);
    const {deviceStore} = useStores();

    const {type, device} = props;

    const handleEdit = () => {
        if (type === HomeComponent.CAPABILITY && device) {
            deviceStore.getDevice(device.id);
        }
        refEdit.current.openForm();
        if (props.handleEdit !== undefined) {
            props.handleEdit();
        }
    };

    const handleDelete = () => {
        refDelete.current.openDialog();
        if (props.handleDelete !== undefined) {
            props.handleDelete();
        }
    };

    const getEditForm = () => {
        if (type === HomeComponent.CAPABILITY) {
            return (<UpdateCapabilityForm ref={refEdit} type={HomeComponent.CAPABILITY} {...props}/>);
        }
    };

    return (
        <GridItem xs={props.xs || 12} sm={props.sm || 6} md={props.md || 3}>
            <BooleanDialog ref={refDelete} {...props}/>
            {getEditForm()}
            <div className={infoClasses.general}>
                <Card>
                    <CardHeader color={props.color || "info"} className={infoClasses.container} {...props}>
                        <h4 className={infoClasses.title}>{props.title || "Add Item"}</h4>
                    </CardHeader>
                    <CardBody>
                        {props.children}
                    </CardBody>
                    {!props.hideFooter &&
                    <BartGeneralFooterCard handleDelete={handleDelete} handleEdit={handleEdit} {...props}/>}
                </Card>
            </div>
        </GridItem>
    );
}