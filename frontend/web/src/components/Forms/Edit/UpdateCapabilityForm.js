import React, {forwardRef} from "react";
import {useParams} from "react-router-dom";
import {HomeComponent} from "../../../index";
import GridItem from "../../Grid/GridItem";
import {EditForm} from "./EditForm";
import TextField from "@material-ui/core/TextField";
import useStores from "../../../hooks/useStores";


export const UpdateCapabilityForm = forwardRef((props, ref) => {
    const {device, capability} = props;
    const {deviceStore} = useStores();

    const [deviceName, setDeviceName] = React.useState(device.name || "");
    const [capName, setCapName] = React.useState(capability.name || "");

    const [errorDeviceName, setErrorDevice] = React.useState(false);
    const [errorCapName, setErrorCap] = React.useState(false);

    const {homeID} = useParams();

    React.useEffect(() => {
        return () => {
            clearStates();
        }
    }, []);

    const handleUpdate = () => {
        if (areValidValues()) {
            if (device.name !== deviceName) {
                device.name = deviceName;
                deviceStore.updateDevice(device.id, device);
            }
            if (capability.name !== capName) {
                capability.name = capName;
                deviceStore.updateCapability(device.id,capability.id,capability);
            }
        }
    };

    const areValidValues = () => {
        return (!errorDeviceName && !errorCapName && deviceName.length !== 0 && capName.length !== 0);
    };

    const changeDeviceName = (event) => {
        const value = event.target.value;
        setErrorDevice(value.length === 0);
        setDeviceName(value);
    };

    const changeCapName = (event) => {
        const value = event.target.value;
        setErrorCap(value.length === 0);
        setCapName(value);
    };

    const clearStates = () => {
        setDeviceName("");
        setCapName("");
    };

    return (
        <EditForm ref={ref} type={HomeComponent.CAPABILITY} {...props} handleUpdate={handleUpdate}
                  areValidValues={areValidValues}>
            <GridItem xs={12} sm={12} md={9}>
                <TextField label="Device name" defaultValue={device.name || ""}
                           onChange={changeDeviceName} error={errorDeviceName} margin={"dense"}/>
                <br/>
                <TextField label="Capability name" defaultValue={capability.name || ""}
                           onChange={changeCapName} error={errorCapName} margin={"dense"}
                />
            </GridItem>
        </EditForm>
    );

});