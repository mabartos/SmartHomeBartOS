import React from "react";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles(style => ({
    container: {
        align: "center",
        alignItems: "center",
        textAlign: "center",
        width: "100%"
    }
}));

export default function NoItemsAvailable(props) {
    const classes = useStyles();

    return (
        <div className={classes.container}>
            <h3> {props.message || "No items found"} </h3>
        </div>
    );
}