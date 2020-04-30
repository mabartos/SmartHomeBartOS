import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import React from "react";
import GeneralService from "../../../services/GeneralService";
import BartStateButton from "../BartStateButton";

export default function RelayCapCard(props) {
    const {capability, homeID, roomID, mqtt,data} = props;
    const id = capability.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.RELAY.topic, id);

    const [isTurnedOn, setIsTurnedOn] = React.useState(capability.isTurnedOn);

    React.useEffect(() => {
        if (data.topic === topic) {
            const object = GeneralService.getObjectFromString(data.payloadString);
            if (object !== null) {
                setIsTurnedOn(object.isTurnedOn);
            }
        }
    }, [data]);

    const handleChangeState = (state) => {
        const isTurnedOn = state;
        setIsTurnedOn(isTurnedOn);
        let result = {};
        result.isTurnedOn = isTurnedOn;
        mqtt.sendWithParams(topic, result, 1, true);
    };

    return (
        <>
            <BartStateButton isTurnedOn={isTurnedOn} onChange={handleChangeState}/>
        </>
    )
}