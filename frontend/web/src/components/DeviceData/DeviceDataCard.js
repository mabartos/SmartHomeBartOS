import React from "react";
import {useObserver} from "mobx-react-lite";
import GeneralInfoCard from "../BartCard/GeneralInfoCard";
import PropTypes from "prop-types";
import {CapabilityType} from "../../index";
import TemperatureCapCard from "./Temperature/TemperatureCapCard";

export default function DeviceDataCard(props) {

    return useObserver(() => {
        const determineDevice = () => {
            switch (props.device.type) {
                case CapabilityType.NONE.name:
                    break;
                case CapabilityType.TEMPERATURE.name:
                    return (<TemperatureCapCard {...props}/>);
                case CapabilityType.HUMIDITY.name:
                    break;
                case CapabilityType.HEATER.name:
                    break;
                case CapabilityType.LIGHT.name:
                    break;
                case CapabilityType.RELAY.name:
                    break;
                case CapabilityType.SOCKET.name:
                    break;
                case CapabilityType.PIR.name:
                    break;
                case CapabilityType.GAS_SENSOR.name:
                    break;
                case CapabilityType.STATISTICS.name:
                    break;
                case CapabilityType.AIR_CONDITIONER.name:
                    break;
                case CapabilityType.OTHER.name:
                    break;
                default:
                    break;
            }
        };

        return (
            <GeneralInfoCard color={props.color} title={props.device.name}>
                {determineDevice()}
            </GeneralInfoCard>
        )
    });
}

DeviceDataCard.propTypes = {
    device: PropTypes.object,
    data: PropTypes.any,
    homeID: PropTypes.string,
    roomID: PropTypes.string
};