/*eslint-disable*/
import React from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
import Drawer from "@material-ui/core/Drawer";
import Hidden from "@material-ui/core/Hidden";
import List from "@material-ui/core/List";
import styles from "assets/jss/material-dashboard-react/components/sidebarStyle.js";
import SidebarItem from "./SidebarItem";
import SidebarProfile from "./SidebarProfile";
import GridContainer from "../Grid/GridContainer";
import BartOSTitle from "../Typography/BartOSTitle";

const useStyles = makeStyles(styles);

export default function Sidebar(props) {
    const classes = useStyles();

    const {color, logo, image, logoText, routes} = props;
    const links = () => {
        let myProps, myKeys;
        routes.map((prop, key) => {
            myProps = prop;
            myKeys = key;
        });

        return (
            <List className={classes.list}>
                {routes.map((prop, key) => {
                    return (<SidebarItem myProp={prop} myKey={key} color={color}/>)
                })}
            </List>
        );
    };
    let brand = (
        <div className={classes.logo}>
            <div
                className={classes.logoLink}
            >
                <GridContainer>
                    <div className={classes.logoImage}>
                        <img src={logo} alt="logo" className={classes.img}/>
                    </div>
                    <div className={classes.brandTitle}>
                        {logoText}
                        <br/>
                        <BartOSTitle/>
                    </div>
                </GridContainer>
            </div>
        </div>
    );
    return (
        <>
            <Hidden mdUp implementation="css">
                <Drawer
                    variant="temporary"
                    anchor={props.rtlActive ? "left" : "right"}
                    open={props.open}
                    classes={{
                        paper: classNames(classes.drawerPaper, {
                            [classes.drawerPaperRTL]: props.rtlActive
                        })
                    }}
                    onClose={props.handleDrawerToggle}
                    ModalProps={{
                        keepMounted: true // Better open performance on mobile.
                    }}
                >
                    {brand}
                    <div className={classes.sidebarWrapper}>
                        {links()}
                    </div>
                    {image !== undefined ? (
                        <div
                            className={classes.background}
                            style={{backgroundImage: "url(" + image + ")"}}
                        />
                    ) : null}
                </Drawer>
            </Hidden>
            <Hidden smDown implementation="css">
                <Drawer
                    anchor={props.rtlActive ? "right" : "left"}
                    variant="permanent"
                    open
                    classes={{
                        paper: classNames(classes.drawerPaper, {
                            [classes.drawerPaperRTL]: props.rtlActive
                        })
                    }}
                >
                    {brand}
                    <div className={classes.sidebarWrapper}>{links()}</div>
                    {image !== undefined ? (
                        <div
                            className={classes.background}
                            style={{backgroundImage: "url(" + image + ")"}}
                        />
                    ) : null}
                    <SidebarProfile/>
                </Drawer>
            </Hidden>
        </>
    );
}

Sidebar.propTypes = {
    rtlActive: PropTypes.bool,
    handleDrawerToggle: PropTypes.func,
    bgColor: PropTypes.oneOf(["purple", "blue", "green", "orange", "red"]),
    logo: PropTypes.string,
    image: PropTypes.string,
    logoText: PropTypes.string,
    routes: PropTypes.arrayOf(PropTypes.object),
    open: PropTypes.bool
};
