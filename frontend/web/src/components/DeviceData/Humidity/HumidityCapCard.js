import {useObserver} from "mobx-react-lite";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import React from "react";
import GeneralService from "../../../services/GeneralService";
import CircularProgress from "../../ProgressBar/CircularProgress";

export default function HumidityCapCard(props) {
    const {capability, homeID, roomID} = props;

    const id = capability.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.HUMIDITY.topic, id);

    const [value, setValue] = React.useState(capability.value || 0);
    const [data, setData] = React.useState("");

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
                <CircularProgress value={value} units={"%"} minValue={0} maxValue={100}/>
            </div>
        )
    });
}