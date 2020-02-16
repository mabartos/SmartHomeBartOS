import React from "react";
import BartGeneralHeaderCard from "./BartGeneralHeaderCard";
import {makeStyles} from "@material-ui/core/styles";
import CardFooter from "components/Card/CardFooter.js";
import CardIcon from "components/Card/CardIcon.js";
import GridItem from "components/Grid/GridItem.js";
import Card from "components/Card/Card.js";

import styles from "assets/jss/material-dashboard-react/components/generalTile.js";
import dashboardStyles from "assets/jss/material-dashboard-react/views/dashboardStyle.js";
import DateRange from "@material-ui/icons/DateRange";
import SettingsIcon from "@material-ui/icons/Settings";

const useStyles = makeStyles(styles);
const useDashboardStyle = makeStyles(dashboardStyles);

const iconStyles = makeStyles(iconStyle => ({
    settings: {
        height: "30px",
        color: "gray",
        align: "center",
        flex: 1
    }
}));

export default function GeneralCard(props) {
    const classes = useStyles();
    const dashboardStyle = useDashboardStyle();
    const iconStyle = iconStyles();

    return (
        <GridItem xs={12} sm={6} md={3}>
            <a>
                <Card className={classes.container} onClick={e => console.log("sd")}>
                    <BartGeneralHeaderCard title={props.title} active={props.active} message={props.message}
                                           color={props.color}/>
                    <CardFooter stats/>
                    {props.children}
                    <CardFooter stats>
                        <div className={dashboardStyle.stats}>
                            <DateRange/>
                            {props.notification || "Last 24 Hours"}
                        </div>
                        <CardIcon>
                            <SettingsIcon className={iconStyle.settings} color={"secondary"}/>
                        </CardIcon>
                    </CardFooter>
                </Card>
            </a>
        </GridItem>
    );
}