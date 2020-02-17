import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// nodejs library to set properties for components
import PropTypes from "prop-types";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
// core components
import styles from "assets/jss/material-dashboard-react/components/cardIconStyle.js";
// @material-ui/icons

const useStyles = makeStyles(styles);

export default function CardIcon(props) {
    const classes = useStyles();
    const {className, children, color, small, colorIcon, ...rest} = props;
    const cardIconClasses = classNames({
        [classes.cardIcon]: !small,
        [classes.smallIcon]: small,
        [classes[color + "CardHeader"]]: color,
        [className]: className !== undefined,

    });
    return (
        <div className={cardIconClasses} {...rest}>
            {children}
        </div>
    );
}

CardIcon.propTypes = {
    className: PropTypes.string,
    color: PropTypes.oneOf([
        "warning",
        "success",
        "danger",
        "info",
        "primary",
        "rose"
    ]),
    children: PropTypes.node
};
