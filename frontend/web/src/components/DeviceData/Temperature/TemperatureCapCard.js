import React from "react";

import {useObserver} from "mobx-react-lite";
import GridItem from "../../Grid/GridItem";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../index";
import GeneralService from "../../../services/GeneralService";

export default function TemperatureCapCard(props) {
    const {device, homeID, roomID, mqtt} = props;

    const id = device.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.TEMPERATURE.topic, id);

    const [value, setValue] = React.useState(device.value);
    const [units, setUnits] = React.useState("°C");

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
                {value || "NaN"}{" "}
                {units}

                <br/>
                {id}
            </GridItem>
        )

    });
}