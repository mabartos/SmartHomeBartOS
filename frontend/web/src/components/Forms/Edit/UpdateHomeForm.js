import React, {forwardRef} from "react";
import GridItem from "../../Grid/GridItem";
import CustomInput from "../../CustomInput/CustomInput";
import GridContainer from "../../Grid/GridContainer";
import {makeStyles} from "@material-ui/core/styles";
import {EditForm} from "./EditForm";
import useStores from "../../../hooks/useStores";
import {BROKER_URL_REGEX} from "../../../index";

const useStyles = makeStyles(theme => ({
    label: {
        paddingTop: "20px"
    },
    broker: {}
}));

export const UpdateHomeForm = forwardRef((props, ref) => {
    const classes = useStyles();

    const [name, setName] = React.useState(props.title);
    const [brokerURL, setBrokerURL] = React.useState(props.brokerURL);

    const [errorName, setErrorName] = React.useState(false);
    const [errorBrokerURL, setErrorBrokerURL] = React.useState(false);

    const {homeStore} = useStores();

    const handleUpdate = () => {
        let update = props.home;
        if (update !== undefined && !errorName && !errorBrokerURL) {
            update.name = name;
            update.brokerURL = brokerURL;
            homeStore.updateHome(update.id, update);
        }
    };

    const changeName = (event) => {
        const value = event.target.value;
        setErrorName(value.length === 0);
        setName(value);
    };

    const validateBrokerURL = (value) => {
        const patt = new RegExp(BROKER_URL_REGEX);
        return (value.length !== 0) ? patt.test(value) : true;
    };

    const changeBrokerURL = (event) => {
        const value = event.target.value;
        setErrorBrokerURL(!validateBrokerURL(value));
        setBrokerURL(value);
    };

    const areValidValues = () => {
        return !errorName && !errorBrokerURL && name.length !== 0;
    };

    return (
        <EditForm ref={ref} type={props.type} {...props} handleUpdate={handleUpdate} areValidValues={areValidValues}>
            <GridContainer>
                <GridItem xs={12} sm={12} md={3}>
                    <div className={classes.label}>Name</div>
                </GridItem>
                <GridItem xs={12} sm={12} md={9}>
                    <CustomInput
                        labelText={props.title || "Name"}
                        id="username"
                        error={errorName}
                        onChange={changeName}
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
                        onChange={changeBrokerURL}
                        error={errorBrokerURL}
                        formControlProps={{
                            fullWidth: true
                        }}
                    />
                </GridItem>
            </GridContainer>
        </EditForm>
    );
});