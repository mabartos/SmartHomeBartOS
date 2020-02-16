import React from "react";
import CardHeader from "components/Card/CardHeader.js";
import {makeStyles} from "@material-ui/core/styles";
import classNames from "classnames";

import Store from "@material-ui/icons/Store";
import CardIcon from "components/Card/CardIcon.js";

import styles from "assets/jss/material-dashboard-react/components/generalTile.js";

const useStyles = makeStyles(styles);

const generalHeaderStyle = makeStyles(style => ({
    title:{
        fontSize:"100%"
    }
}));

export default function BartGeneralHeaderCard({title, active = false, color}) {
    const classes = useStyles();
    const headerStyle=generalHeaderStyle();
    const colorActivity = active ? classes.cardStatusActive : classes.cardStatusInActive;
    const isActive = active ? "Active" : "InActive";
    const titleClasses=classNames({
       [classes.cardTitle]:true,
       [headerStyle.title]:true
    });

    return (
        <CardHeader stats icon>
            <CardIcon color={color}>
                <Store/>
            </CardIcon>
            <p className={colorActivity}>
                {isActive}
            </p>
            <h3 className={titleClasses}>
                {title}
            </h3>
        </CardHeader>
    );
}