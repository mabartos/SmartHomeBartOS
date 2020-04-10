import React from "react";
import {useObserver} from "mobx-react-lite";
import GeneralInfoCard from "../BartCard/GeneralInfoCard";
import PropTypes from "prop-types";
import {CapabilityType} from "../../constants/Capabilities";
import TemperatureCapCard from "./Temperature/TemperatureCapCard";
import useStores from "../../hooks/useStores";
import LightsCapCard from "./Light/LightsCapCard";
import HumidityCapCard from "./Humidity/HumidityCapCard";

export default function DeviceDataCard(props) {
    const {deviceStore} = useStores();
    const {color, device} = props;

    const removeDeviceFromRoom = () => {
        deviceStore.removeDeviceFromRoom(device.deviceID);
    };

    const deleteDevice = () => {
        deviceStore.deleteDevice(device.deviceID);
    };

    return useObserver(() => {
        const determineDevice = () => {
            switch (props.device.type) {
                case CapabilityType.NONE.name:
                    break;
                case CapabilityType.TEMPERATURE.name:
                    return (<TemperatureCapCard {...props}/>);
                case CapabilityType.HUMIDITY.name:
                    return (<HumidityCapCard {...props}/>);
                case CapabilityType.HEATER.name:
                    break;
                case CapabilityType.LIGHT.name:
                    return (<LightsCapCard {...props}/>);
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
            <GeneralInfoCard color={color} title={device.name} {...props} deleteLabel={"Delete device"}
                             handleDelete={deleteDevice}
                             nextLabel={"Remove device from room"} handleNext={removeDeviceFromRoom}>
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