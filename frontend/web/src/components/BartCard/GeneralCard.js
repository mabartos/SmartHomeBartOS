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
import Poppers from "@material-ui/core/Popper";

const useStyles = makeStyles(styles);
const useDashboardStyle = makeStyles(dashboardStyles);

const iconStyles = makeStyles(iconStyle => ({
    settings: {
        height: "30px",
        color: "gray",
        align: "center",
        flex: 1
    },
    popperContainer: {
        backgroundColor: "red"
    },
    popperText: {
        border: '1px solid',
        color: "black",
        backgroundColor: "red"
    }
}));

export default function GeneralCard(props) {
    const classes = useStyles();
    const dashboardStyle = useDashboardStyle();
    const iconStyle = iconStyles();

    const [settingsAnchor, setSettingsAnchor] = React.useState(null);

    const handleClickSettings = event => {
        setSettingsAnchor(settingsAnchor ? null : event.currentTarget);
    };

    const onSelect = () => {
        if (props.onClick !== undefined) {
            props.onClick();
            return;
        }
        console.log('Item selected!');
    };

    return (
        <GridItem xs={12} sm={6} md={3}>
            <Clickable onClick={() => onSelect()}>
                <Card className={classes.container}>
                    <BartGeneralHeaderCard title={props.title} active={props.active} color={props.color}
                                           displayActivity={props.displayActivity}/>

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
                                              onClick={handleClickSettings}/>
                            </CardIcon>
                            <Poppers id={'simple'} open={Boolean(settingsAnchor)} anchorEl={settingsAnchor}
                                     placement={'top'}
                                     transition>
                                <div className={classes.popperText}>AHOJ</div>
                            </Poppers>
                        </StopPropagation>
                    </CardFooter>
                </Card>
            </Clickable>
        </GridItem>
    );
}