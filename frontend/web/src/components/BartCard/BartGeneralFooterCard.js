import {StopPropagation} from "react-clickable";
import DateRange from "@material-ui/icons/DateRange";
import SettingsIcon from "@material-ui/icons/Settings";
import Poppers from "@material-ui/core/Popper";
import {ClickAwayListener} from "@material-ui/core";
import Paper from "@material-ui/core/Paper";
import MenuList from "@material-ui/core/MenuList";
import {HomeComponent} from "../../index";
import MenuItem from "@material-ui/core/MenuItem";
import Divider from "@material-ui/core/Divider";
import React from "react";
import {makeStyles} from "@material-ui/core/styles";

import dashboardStyles from "assets/jss/material-dashboard-react/views/dashboardStyle.js";
import dropDown from "assets/jss/material-dashboard-react/dropdownStyle.js";

import CardFooter from "../Card/CardFooter";
import CardIcon from "../Card/CardIcon";

const useDashboardStyle = makeStyles(dashboardStyles);
const useDropDownStyle = makeStyles(dropDown);


const generalStyle = makeStyles(style => ({
    toForeground: {
        zIndex: "3 !important"
    }
}));

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

export default function BartGeneralFooterCard(props) {
    const dashboardStyle = useDashboardStyle();
    const iconStyle = iconStyles();
    const dropDownStyle = useDropDownStyle();
    const classes=generalStyle();

    const {type, editLabel, deleteLabel, nextLabel} = props;

    const [settingsAnchor, setSettingsAnchor] = React.useState(null);
    const [openSettingsAnchor, setOpenSettingsAnchor] = React.useState(false);

    const handleClickSettings = event => {
        setOpenSettingsAnchor(!openSettingsAnchor);
        setSettingsAnchor(openSettingsAnchor ? null : event.currentTarget);
    };

    const closeSettings = () => {
        setOpenSettingsAnchor(false);
    };

    //Implement
    const handleAddDevice = () => {
        if (props.handleAddDevice !== undefined) {
            props.handleAddDevice();
        }
    };
    const handleEdit = () => {
        if (props.handleEdit !== undefined) {
            props.handleEdit();
        }
    };
    const handleDelete = () => {
        if (props.handleDelete !== undefined) {
            props.handleDelete();
        }
    };

    const handleNext = () => {
        if (props.handleNext !== undefined) {
            props.handleNext();
            closeSettings();
        }
    };


    return (
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
                         transition className={classes.toForeground}>
                    <ClickAwayListener onClickAway={closeSettings}>
                        <Paper>
                            <MenuList role="menu">
                                {type === HomeComponent.ROOM && (
                                    <MenuItem className={dropDownStyle.dropdownItem}
                                              onClick={handleAddDevice}>
                                        Add device
                                    </MenuItem>
                                )}
                                {nextLabel && (
                                    <MenuItem className={dropDownStyle.dropdownItem}
                                              onClick={handleNext}>
                                        {nextLabel || "Next"}
                                    </MenuItem>
                                )}

                                <MenuItem className={dropDownStyle.dropdownItem} onClick={handleEdit}>
                                    {editLabel || "Edit"}
                                </MenuItem>
                                <Divider light/>
                                <MenuItem className={dropDownStyle.dropdownItem} onClick={handleDelete}>
                                    <span style={{color: "red"}}>{deleteLabel || "Delete"}</span>
                                </MenuItem>
                            </MenuList>
                        </Paper>
                    </ClickAwayListener>
                </Poppers>
            </StopPropagation>
        </CardFooter>
    )

}