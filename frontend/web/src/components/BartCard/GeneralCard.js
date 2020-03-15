import React from "react";
import BartGeneralHeaderCard from "./BartGeneralHeaderCard";
import {makeStyles} from "@material-ui/core/styles";
import CardFooter from "components/Card/CardFooter.js";
import CardIcon from "components/Card/CardIcon.js";
import GridItem from "components/Grid/GridItem.js";
import Card from "components/Card/Card.js";

import styles from "assets/jss/material-dashboard-react/components/generalTile.js";
import dashboardStyles from "assets/jss/material-dashboard-react/views/dashboardStyle.js";
import dropDown from "assets/jss/material-dashboard-react/dropdownStyle.js";

import DateRange from "@material-ui/icons/DateRange";
import SettingsIcon from "@material-ui/icons/Settings";
import {Clickable, StopPropagation} from "react-clickable";
import Poppers from "@material-ui/core/Popper";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import MenuList from "@material-ui/core/MenuList";
import MenuItem from "@material-ui/core/MenuItem";
import Divider from "@material-ui/core/Divider";
import Paper from "@material-ui/core/Paper";
import BooleanDialog from "../BartDialogs/BooleanDialog";
import {useParams} from "react-router-dom";


const useStyles = makeStyles(styles);
const useDashboardStyle = makeStyles(dashboardStyles);
const useDropDownStyle = makeStyles(dropDown);

const iconStyles = makeStyles(iconStyle => ({
    settings: {
        height: "30px",
        color: "gray",
        align: "center",
        flex: 1
    },
    popperTextWarning: {
        color: "red"
    },
}));

export default function GeneralCard(props) {
    const classes = useStyles();
    const dashboardStyle = useDashboardStyle();
    const dropDownStyle = useDropDownStyle();
    const iconStyle = iconStyles();

    const [settingsAnchor, setSettingsAnchor] = React.useState(null);
    const [openWarningDialog, setWarningDialog] = React.useState(false);

    const handleClickSettings = event => {
        setSettingsAnchor(settingsAnchor ? null : event.currentTarget);
    };

    const handleEdit = () => {
        console.log("edit");
    };

    const handleDelete = () => {
        setWarningDialog(true);
    };

    const closeSettings = () => {
        setSettingsAnchor(null);
        setWarningDialog(false);
    };

    const onSelect = () => {
        if (props.onClick !== undefined) {
            props.onClick();
            return;
        }
        console.log('Item selected!');
    };

    const showDialog = () => {
        if (openWarningDialog) {
            return (<BooleanDialog open={openWarningDialog} ids={props.rest}/>)
        }
    };

    return (
        <GridItem xs={12} sm={6} md={3}>
            {showDialog()}
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
                                <Paper>
                                    <ClickAwayListener onClickAway={closeSettings}>
                                        <MenuList role="menu">
                                            <MenuItem className={dropDownStyle.dropdownItem} onClick={handleEdit}>
                                                Edit
                                            </MenuItem>
                                            <Divider light/>
                                            <MenuItem className={dropDownStyle.dropdownItem} onClick={handleDelete}>
                                                <span style={{color: "red"}}>Delete</span>
                                            </MenuItem>
                                        </MenuList>
                                    </ClickAwayListener>
                                </Paper>
                            </Poppers>
                        </StopPropagation>
                    </CardFooter>
                </Card>
            </Clickable>
        </GridItem>
    );
}