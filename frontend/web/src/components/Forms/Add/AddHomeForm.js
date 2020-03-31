import React, {forwardRef} from "react";
import GridItem from "../../Grid/GridItem";
import CustomInput from "../../CustomInput/CustomInput";
import GridContainer from "../../Grid/GridContainer";
import {BROKER_URL_REGEX, HomeComponent} from "../../../index";
import {AddForm} from "./AddForm";
import useStores from "../../../hooks/useStores";

export const AddHomeForm = forwardRef((props, ref) => {
    const {homeStore} = useStores();

    let name = "";
    let brokerURL = "";

    const [errorName, setErrorName] = React.useState(false);
    const [errorBrokerURL, setErrorBrokerURL] = React.useState(false);

    const changeName = (event) => {
        const value = event.target.value;
        setErrorName(value.length === 0);
        name = value;
    };

    const validateBrokerURL = (value) => {
        const patt = new RegExp(BROKER_URL_REGEX);
        return (value.length !== 0) ? patt.test(value) : true;
    };

    const changeBrokerURL = (event) => {
        const value = event.target.value;
        setErrorBrokerURL(!validateBrokerURL(value));
        brokerURL = value;
    };

    const createHome = () => {
        let home = {};
        if (areValidValues()) {
            home.name = name;
            home.brokerURL = brokerURL;
            homeStore.createHome(home);
        }
    };

    const areValidValues = () => {
        return !errorName && !errorBrokerURL && name.length !== 0;
    };

    return (
        <AddForm ref={ref} type={HomeComponent.HOME} {...props} handleAdd={createHome} areValidValues={areValidValues}>
            <GridContainer>
                <GridItem xs={12} sm={12} md={9}>
                    <CustomInput
                        labelText={"Name"}
                        id="name"
                        error={errorName}
                        onChange={changeName}
                        formControlProps={{
                            fullWidth: true
                        }}
                    />
                </GridItem>
                <GridItem xs={12} sm={12} md={9}>
                    <CustomInput
                        labelText={"Broker URL"}
                        id="brokerURL"
                        onChange={changeBrokerURL}
                        error={errorBrokerURL}
                        formControlProps={{
                            fullWidth: true
                        }}
                    />
                </GridItem>
            </GridContainer>
        </AddForm>
    );
});