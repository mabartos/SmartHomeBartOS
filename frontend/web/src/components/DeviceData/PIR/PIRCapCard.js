import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import React from "react";
import GeneralService from "../../../services/GeneralService";
import {Typography} from "@material-ui/core";

export default function PIRCapCard(props) {
    const {capability, homeID, roomID, data} = props;

    const [isTurnedOn, setIsTurnedOn] = React.useState(capability.isTurnedOn);

    const id = capability.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.PIR.topic, id);

    React.useEffect(() => {
        if (data.topic === topic) {
            const object = GeneralService.getObjectFromString(data.payloadString);
            if (object !== null) {
                setIsTurnedOn(object.isTurnedOn);
            }
        }
    }, [data]);

    console.log(isTurnedOn);

    return (
        <>
            <Typography>
                {isTurnedOn && "ON"}
                {!isTurnedOn && "OFF"}
            </Typography>

        </>
    )

}