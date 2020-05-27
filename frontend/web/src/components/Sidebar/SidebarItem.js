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
        return window.location.href.indexOf(routeName) > -1;
    }

    const {myProp, myKey} = props;

    const getItem = (itemProp, itemKey) => {
        let activePro = " ";
        let listItemClasses = classNames({
            [" " + classes[props.color]]: activeRoute(itemProp.layout + itemProp.path)
        });
        const whiteFontClasses = classNames({
            [" " + classes.whiteFont]: activeRoute(itemProp.layout + itemProp.path)
        });

        const showStringIcon = () => (
            <Icon
                className={classNames(classes.itemIcon, whiteFontClasses, {
                    [classes.itemIconRTL]: props.rtlActive
                })}
            >
                {props.myIcon ? props.myIcon : itemProp.icon}
            </Icon>
        );

        const showRoutesIcon = () => (
            <itemProp.icon
                className={classNames(classes.itemIcon, whiteFontClasses, {
                    [classes.itemIconRTL]: props.rtlActive
                })}
            />
        );

        const showIcon = () => {
            return (typeof itemProp.icon === "string") ? showStringIcon() : showRoutesIcon();
        };

        return (
            <NavLink
                to={itemProp.layout + itemProp.path}
                className={activePro + classes.item}
                activeClassName="active"
                key={itemKey}
            >
                <ListItem button className={classes.itemLink + listItemClasses}>
                    {showIcon()}
                    <ListItemText
                        primary={props.name ? props.name : itemProp.name}
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
        getItem(myProp, myKey)
    )

}