import React from "react";
import {makeStyles} from "@material-ui/core/styles";
import GridItem from "components/Grid/GridItem.js";
import defaultImage from "assets/img/sidebar-2.jpg";

import GeneralCard from "./GeneralCard";
import useStores from "../../hooks/useStores";

const useStyles = makeStyles(styles => ({
    homePictureContainer: {
        height: "180px",
        borderRadius: "10px",
        align: "center"
    },
    homePicture: {
        height: "100%",
        width: "100%",
        borderRadius: "5px",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover"
    }
}));

export default function HomeCard(props) {
    const classes = useStyles();
   // const {homeStore} = useStores().homeStore;

    const onSelect = () => {
                console.log("Home");
    };

    return (
        <GeneralCard onClick={()=>onSelect()} title={props.title} active={props.active} color={props.color} displayActivity={true}>
            <GridItem xs={12} sm={12} md={12}>
                <div className={classes.homePictureContainer}>
                    <img className={classes.homePicture} alt="home" src={props.image || defaultImage}/>
                </div>
            </GridItem>
        </GeneralCard>
    );
}