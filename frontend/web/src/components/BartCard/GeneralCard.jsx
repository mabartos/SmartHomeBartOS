import React, {useRef} from "react";
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
import MenuList from "@material-ui/core/MenuList";
import MenuItem from "@material-ui/core/MenuItem";
import Divider from "@material-ui/core/Divider";
import Paper from "@material-ui/core/Paper";
import {BooleanDialog} from "../BartDialogs/BooleanDialog";
import {HomeComponent} from "../../index";
import {UpdateHomeForm} from "../Forms/Edit/UpdateHomeForm";
import {ClickAwayListener} from "@material-ui/core";
import {UpdateRoomForm} from "../Forms/Edit/UpdateRoomForm";
import {AddDeviceToRoom} from "../BartDialogs/AddDeviceToRoom";

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

    const refDelete = useRef(null);
    const refEdit = useRef(null);
    const refAddDevice = useRef(null);

    const [settingsAnchor, setSettingsAnchor] = React.useState(null);
    const [openSettingsAnchor,setOpenSettingsAnchor]=React.useState(false);

    const handleClickSettings = event => {
        setOpenSettingsAnchor(!openSettingsAnchor);
        setSettingsAnchor(openSettingsAnchor ? null : event.currentTarget);
    };

    const handleEdit = () => {
        refEdit.current.openForm();
    };

    const handleDelete = () => {
        refDelete.current.openDialog()
    };

    const closeSettings = () => {
        setOpenSettingsAnchor(false);
    };

    const onSelect = () => {
        if (props.onClick !== undefined) {
            props.onClick();
            return;
        }
        console.log('Item selected!');
    };

    const handleAddDevice = () => {
        refAddDevice.current.openDialog();
    };

    const getEditForm = () => {
        switch (props.type) {
            case HomeComponent.HOME:
                return (<UpdateHomeForm ref={refEdit} {...props}/>);
            case HomeComponent.USER:
                break;
            case HomeComponent.ROOM:
                return (<UpdateRoomForm ref={refEdit} {...props}/>);
            case HomeComponent.DEVICE:
                break;
        }
    };

    return (
        <GridItem xs={12} sm={6} md={3}>
            <BooleanDialog ref={refDelete} type={props.type} {...props}/>
            {getEditForm()}
            <AddDeviceToRoom ref={refAddDevice} {...props}/>
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
                            <Poppers id={'simple'} open={openSettingsAnchor} anchorEl={settingsAnchor}
                                     placement={'top'}
                                     transition>
                                <ClickAwayListener onClickAway={closeSettings}>
                                    <Paper>
                                        <MenuList role="menu">
                                            {props.type === HomeComponent.ROOM && (
                                                <MenuItem className={dropDownStyle.dropdownItem}
                                                          onClick={handleAddDevice}>
                                                    Add device
                                                </MenuItem>
                                            )}
                                            <MenuItem className={dropDownStyle.dropdownItem} onClick={handleEdit}>
                                                Edit
                                            </MenuItem>
                                            <Divider light/>
                                            <MenuItem className={dropDownStyle.dropdownItem} onClick={handleDelete}>
                                                <span style={{color: "red"}}>Delete</span>
                                            </MenuItem>
                                        </MenuList>
                                    </Paper>
                                </ClickAwayListener>
                            </Poppers>
                        </StopPropagation>
                    </CardFooter>
                </Card>
            </Clickable>
        </GridItem>
    );
}