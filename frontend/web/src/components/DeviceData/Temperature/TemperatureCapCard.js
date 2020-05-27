import React from "react";

import {useObserver} from "mobx-react-lite";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import GeneralService from "../../../services/GeneralService";
import 'react-circular-progressbar/dist/styles.css';
import CircularProgress from "../../ProgressBar/CircularProgress";

export default function TemperatureCapCard(props) {
    const {capability, homeID, roomID} = props;

    const id = capability.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.TEMPERATURE.topic, id);

    const [value, setValue] = React.useState(capability.value || 0);
    const [units, setUnits] = React.useState(capability.units || "°C");

    const [data, setData] = React.useState("");

    React.useEffect(() => {
        if (props.data.topic === topic) {
            const object = GeneralService.getObjectFromString(props.data.payloadString);
            if (object !== null) {
                setData(object);
                setValue(object.actual);
            }
        }
    }, [props.data, topic]);

    return useObserver(() => {
        return (
                <CircularProgress value={value} units={units} minValue={-20} maxValue={50}/>
        )
    });
}