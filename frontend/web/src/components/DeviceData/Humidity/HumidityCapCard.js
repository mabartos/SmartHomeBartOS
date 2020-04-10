import {useObserver} from "mobx-react-lite";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import React from "react";
import GeneralService from "../../../services/GeneralService";
import GridItem from "../../Grid/GridItem";
import CircularProgress from "../../ProgressBar/CircularProgress";

export default function HumidityCapCard(props) {
    const {device, homeID, roomID} = props;

    const id = device.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.HUMIDITY.topic, id);

    const [value, setValue] = React.useState(device.value);
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
            <GridItem xs={12} sm={12} md={12}>
                {id}
                <CircularProgress value={value} units={"%"} minValue={0} maxValue={100}/>
            </GridItem>
        )
    });
}