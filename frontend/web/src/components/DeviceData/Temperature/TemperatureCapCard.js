import React from "react";

import {useObserver} from "mobx-react-lite";
import GridItem from "../../Grid/GridItem";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../index";
import GeneralService from "../../../services/GeneralService";

export default function TemperatureCapCard(props) {
    const id = props.device.id;

    const topic = DeviceService.getFullTopic(props.homeID, props.roomID, CapabilityType.TEMPERATURE.topic, id);

    const [value, setValue] = React.useState(props.device.value);
    const [units, setUnits] = React.useState(props.device.units);

    const [data, setData] = React.useState("");

    React.useEffect(() => {
        if (props.data.topic === topic) {
            const object = GeneralService.getObjectFromString(props.data.payloadString);
            if (object !== null) {
                setData(object);
                setUnits(data.units);
                setValue(data.value);
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