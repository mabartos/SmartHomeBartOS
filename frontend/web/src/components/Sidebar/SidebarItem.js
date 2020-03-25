import React from "react";
import {makeStyles} from "@material-ui/core/styles";
import styles from "assets/jss/material-dashboard-react/components/sidebarStyle.js";
import classNames from "classnames";
import {NavLink} from "react-router-dom";
import ListItem from "@material-ui/core/ListItem";
import Icon from "@material-ui/core/Icon";
import ListItemText from "@material-ui/core/ListItemText";

const useStyles = makeStyles(styles);

export default function SidebarItem(props) {
    const classes = useStyles();

    function activeRoute(routeName) {
        return window.location.href.indexOf(routeName) > -1 ? true : false;
    }

    const getItem = (prop, key) => {
        var activePro = " ";
        var listItemClasses = classNames({
            [" " + classes[props.color]]: activeRoute(prop.layout + prop.path)
        });
        const whiteFontClasses = classNames({
            [" " + classes.whiteFont]: activeRoute(prop.layout + prop.path)
        });

        const showStringIcon = () => (
            <Icon
                className={classNames(classes.itemIcon, whiteFontClasses, {
                    [classes.itemIconRTL]: props.rtlActive
                })}
            >
                {props.myIcon ? props.myIcon : prop.icon}
            </Icon>
        );

        const showRoutesIcon = () => (
            <prop.icon
                className={classNames(classes.itemIcon, whiteFontClasses, {
                    [classes.itemIconRTL]: props.rtlActive
                })}
            />
        );

        const showIcon = () => {
            return (typeof prop.icon === "string") ? showStringIcon() : showRoutesIcon();
        };

        return (
            <NavLink
                to={prop.layout + prop.path}
                className={activePro + classes.item}
                activeClassName="active"
                key={key}
            >
                <ListItem button className={classes.itemLink + listItemClasses}>
                    {showIcon()}
                    <ListItemText
                        primary={props.name ? props.name : prop.name}
                        className={classNames(classes.itemText, whiteFontClasses, {
                            [classes.itemTextRTL]: props.rtlActive
                        })}
                        disableTypography={true}
                    />
                </ListItem>
            </NavLink>
        );
    };

    return (
        getItem(props.prop, props.key)
    )

}