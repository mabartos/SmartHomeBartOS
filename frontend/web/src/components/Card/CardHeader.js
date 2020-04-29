import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// nodejs library to set properties for components
import PropTypes from "prop-types";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
// core components
import styles from "assets/jss/material-dashboard-react/components/cardHeaderStyle.js";
// @material-ui/icons

const useStyles = makeStyles(styles);
const activityStatusStyles = makeStyles(style => ({
    container: {
        height: "15px",
        width: "15px",
        zIndex: "10",
        position: "absolute",
        left: "0px",
        top: "0px",
        borderRadius: "calc(.25rem - 1px)",
        boxShadow: "0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 3px 10px 0 rgba(0, 0, 0, 0.19)",
    },
    active: {
        backgroundColor: "rgba(50,205,50,0.9)",
    },
    inActive: {
        backgroundColor: "rgba(220,20,60,0.9)",
    },
    disabled: {
        backgroundColor: "rgba(90,90,90,0.8)",
    }
}));

export default function CardHeader(props) {
    const classes = useStyles();
    const statusStyle = activityStatusStyles();

    const {className, children, color, plain, stats, icon, showActivity, active, disabled, ...rest} = props;
    const cardHeaderClasses = classNames({
        [classes.cardHeader]: true,
        [classes[color + "CardHeader"]]: color,
        [classes.cardHeaderPlain]: plain,
        [classes.cardHeaderStats]: stats,
        [classes.cardHeaderIcon]: icon,
        [className]: className !== undefined
    });

    const activityClasses = classNames({
        [statusStyle.container]: showActivity,
        [statusStyle.active]: active,
        [statusStyle.inActive]: !active,
        [statusStyle.disabled]: disabled
    });

    return (
        <>
            <div className={cardHeaderClasses} {...rest}>
                <div className={activityClasses}/>
                {children}
            </div>
        </>
    );
}

CardHeader.propTypes = {
    className: PropTypes.string,
    color: PropTypes.oneOf([
        "warning",
        "success",
        "danger",
        "info",
        "primary",
        "rose"
    ]),
    plain: PropTypes.bool,
    stats: PropTypes.bool,
    icon: PropTypes.bool,
    children: PropTypes.node
};
