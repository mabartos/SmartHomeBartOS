import React from "react";

import {useObserver} from "mobx-react-lite";
import GridItem from "../../Grid/GridItem";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import GeneralService from "../../../services/GeneralService";
import 'react-circular-progressbar/dist/styles.css';
import CircularProgress from "../../ProgressBar/CircularProgress";

export default function TemperatureCapCard(props) {
    const {device, homeID, roomID} = props;

    const id = device.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.TEMPERATURE.topic, id);

    const [value, setValue] = React.useState(device.value);
    const [units, setUnits] = React.useState(device.units);

    const [data, setData] = React.useState("");

    console.log(device.active);
    React.useEffect(() => {
        if (props.data.topic === topic) {
            const object = GeneralService.getObjectFromString(props.data.payloadString);
            if (object !== null) {
                setData(object);
                setValue(object.actual);
            }
        }
    }, [props.data]);

    return useObserver(() => {
        return (
            <div>
                {device.active && <p>TRUE</p>}
                <CircularProgress value={value} units={units} minValue={-20} maxValue={50}/>
            </div>
        )
    });
}