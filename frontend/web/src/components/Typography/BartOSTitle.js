import Typography from "@material-ui/core/Typography";
import React from "react";
import {makeStyles} from "@material-ui/core";

const useStyles = makeStyles(style => ({
        title: {
            display: "inline-block"
        }
    }))
;

export default function BartOSTitle(props) {
    const {variant, component, color, subVariant, subComponent, subColor} = props;
    const classes = useStyles();

    return (
        <>
            <div>
                <Typography className={classes.title} color={color || "primary"} component={component || "h1"}
                            variant={variant || "h5"}>
                    Bart
                </Typography>
                <Typography className={classes.title} component={subComponent || "h1"} variant={subVariant || "h5"}
                            color={subColor || "secondary"}>
                    OS
                </Typography>
            </div>
        </>
    )
};