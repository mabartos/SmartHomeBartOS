import React from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Hidden from "@material-ui/core/Hidden";
// @material-ui/icons
import Menu from "@material-ui/icons/Menu";
// core components
import AdminNavbarLinks from "./AdminNavbarLinks.js";
import Button from "components/CustomButtons/Button.js";
import {useHistory} from "react-router-dom";

import styles from "assets/jss/material-dashboard-react/components/headerStyle.js";
import {Typography} from "@material-ui/core";

const useStyles = makeStyles(styles);

export default function Header(props) {
    const classes = useStyles();
    const history = useHistory();

    function makeBrand() {
        let name;
        props.routes.map(prop => {
            if (window.location.href.indexOf(prop.layout + prop.path) !== -1) {
                name = props.rtlActive ? prop.rtlName : prop.name;
            }
            return null;
        });
        return name;
    }

    const handleClickBrand = () => {
        if (window.location.href.indexOf("/my-homes") !== -1) {
            history.push("/admin/my-homes")
        }
    };

    const goBackInHistory = () => {
        history.goBack();
    };

    const showHomeID = () => {
        if (getHomeID() !== -1) {
            return (<Typography color={"secondary"} variant={"h5"}>
                Home ID: {getHomeID()}
            </Typography>)
        }
    };

    const getHomeID = () => {
        let path = window.location.pathname;
        let index = path.indexOf("/my-homes/");
        if (index !== -1) {
            path = path.substring(index);
            let array = path.split("/");
            return (array.length > 2) ? parseInt(array[2], 10) : -1;
        }
        return -1;
    };

    const getHomeMembers = () => {
        const homeID = getHomeID();
        if (homeID !== -1) {

        }
    };


    const {color} = props;
    const appBarClasses = classNames({
        [" " + classes[color]]: color
    });
    return (
        <AppBar className={classes.appBar + appBarClasses}>
            <Toolbar className={classes.container}>
                <div className={classes.flex}>
                    {/* Here we create navbar brand, based on route name */}
                    <Button color="transparent" href="#" className={classes.title} onClick={goBackInHistory}>
                        Back
                    </Button>
                    <Button color="transparent" onClick={handleClickBrand} className={classes.title}>
                        {makeBrand()}
                    </Button>
                    <Button color="transparent" variant="" className={classes.title}>
                        Home Members
                    </Button>
                    <Button color="transparent" disabled className={classes.title}>
                        {showHomeID()}
                    </Button>
                </div>
                <Hidden smDown implementation="css">
                    <AdminNavbarLinks/>
                </Hidden>
                <Hidden mdUp implementation="css">
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={props.handleDrawerToggle}
                    >
                        <Menu/>
                    </IconButton>
                </Hidden>
            </Toolbar>
        </AppBar>
    );
}

Header.propTypes = {
    color: PropTypes.oneOf(["primary", "info", "success", "warning", "danger"]),
    rtlActive: PropTypes.bool,
    handleDrawerToggle: PropTypes.func,
    routes: PropTypes.arrayOf(PropTypes.object)
};
