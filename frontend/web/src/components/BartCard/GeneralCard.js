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
import {Clickable, StopPropagation} from "react-clickable";

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

    const onSelect = () => {
        console.log('Item selected!');
    };

    return (
        <GridItem xs={12} sm={6} md={3}>
            <Clickable onClick={() => onSelect()}>
                <Card className={classes.container}>
                    <BartGeneralHeaderCard title={props.title} active={props.active} message={props.message}
                                           color={props.color}/>

                    <CardFooter stats/>
                    {props.children}

                    <CardFooter stats>
                        <StopPropagation>
                            <div className={dashboardStyle.stats}>
                                <DateRange/>
                                {props.notification || "Last 24 Hours"}
                            </div>
                        </StopPropagation>
                        <StopPropagation>
                            <CardIcon>
                                <SettingsIcon className={iconStyle.settings} color={"secondary"}
                                              onClick={() => console.log("test")}/>
                            </CardIcon>
                        </StopPropagation>
                    </CardFooter>
                </Card>
            </Clickable>
        </GridItem>
    );
}