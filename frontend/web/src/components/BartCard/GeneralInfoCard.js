import React from "react";
import GridItem from "components/Grid/GridItem.js";
import Card from "components/Card/Card.js";
import {makeStyles} from "@material-ui/core/styles";
import CardHeader from "components/Card/CardHeader.js";
import CardBody from "components/Card/CardBody.js";
import {whiteColor} from "../../assets/jss/material-dashboard-react";


const useInfoStyle = makeStyles(style => ({
    container: {
        color: "primary",
        align: "center",
        textAlign: "center",
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

    const onSelect = () => {
        console.log("ahoj");
    };

    return (
        <GridItem xs={12} sm={6} md={3}>
            <Card>
                <CardHeader color={props.color || "info"} className={useInfoStyle.container}>
                    <h4 className={infoClasses.title}>Add Home</h4>
                </CardHeader>
                <CardBody>
                    {props.children}
                </CardBody>
            </Card>
        </GridItem>
    );
}