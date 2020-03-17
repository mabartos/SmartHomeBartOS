import React from "react";
import GridItem from "../Grid/GridItem";
import CustomInput from "../CustomInput/CustomInput";
import GridContainer from "../Grid/GridContainer";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles(theme => ({
    label: {
        paddingTop: "20px"
    },
    broker: {}
}));

export default function UpdateHomeForm(props) {
    const classes = useStyles();

    return (
        <GridContainer>
            <GridItem xs={12} sm={12} md={3}>
                <div className={classes.label}>Name</div>
            </GridItem>
            <GridItem xs={12} sm={12} md={9}>
                <CustomInput
                    labelText={props.title || "Name"}
                    id="username"
                    formControlProps={{
                        fullWidth: true
                    }}
                />
            </GridItem>
            <GridItem xs={12} sm={12} md={3}>
                <div className={classes.label}>Broker URL</div>
            </GridItem>
            <GridItem xs={12} sm={12} md={9}>
                <CustomInput
                    labelText={props.brokerURL || "Broker URL"}
                    id="brokerURL"
                    formControlProps={{
                        fullWidth: true
                    }}
                />
            </GridItem>
        </GridContainer>
    );
};