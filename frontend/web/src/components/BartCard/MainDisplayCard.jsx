import React from "react";
import {makeStyles} from "@material-ui/core/styles";
import GridItem from "components/Grid/GridItem.js";
import defaultImage from "assets/img/sidebar-2.jpg";
import {useHistory, useRouteMatch} from 'react-router-dom';

import GeneralCard from "./GeneralCard";

const useStyles = makeStyles(styles => ({
    mainPictureContainer: {
        height: "180px",
        borderRadius: "10px",
        align: "center"
    },
    mainPicture: {
        height: "100%",
        width: "100%",
        borderRadius: "5px",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover"
    }
}));

export default function MainDisplayCard(props) {
    const classes = useStyles();

    return (
        <GeneralCard onClick={props.onSelect} displayActivity={true} {...props}>
            <GridItem xs={12} sm={12} md={12}>
                <div className={classes.mainPictureContainer}>
                    <img className={classes.mainPicture} alt="home" src={props.image || defaultImage}/>
                </div>
            </GridItem>
        </GeneralCard>
    );
}