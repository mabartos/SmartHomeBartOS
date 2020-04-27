import React from "react";
import {useObserver} from "mobx-react-lite";
import GeneralInfoCard from "../BartCard/GeneralInfoCard";
import PropTypes from "prop-types";
import {CapabilityType} from "../../constants/Capabilities";
import TemperatureCapCard from "./Temperature/TemperatureCapCard";
import useStores from "../../hooks/useStores";
import LightsCapCard from "./Light/LightsCapCard";
import HumidityCapCard from "./Humidity/HumidityCapCard";
import {HomeComponent} from "../../index";
import {toJS} from "mobx";

export default function DeviceDataCard(props) {
    const {deviceStore} = useStores();
    const {color, capability, homeID} = props;

    React.useEffect(() => {
        deviceStore.getDeviceByID(capability.deviceID);
    }, [deviceStore]);

    const removeDeviceFromRoom = () => {
        deviceStore.removeDeviceFromRoom(capability.deviceID);
    };

    return useObserver(() => {
        const {devices} = deviceStore;

        let tmpDevices = toJS(devices);
        let device = tmpDevices[capability.deviceID];

        const determineDevice = () => {
            switch (capability.type) {
                case CapabilityType.NONE.name:
                    break;
                case CapabilityType.TEMPERATURE.name:
                    return (<TemperatureCapCard device={devices} {...props}/>);
                case CapabilityType.HUMIDITY.name:
                    return (<HumidityCapCard device={devices} {...props}/>);
                case CapabilityType.HEATER.name:
                    break;
                case CapabilityType.LIGHT.name:
                    return (<LightsCapCard device={devices} {...props}/>);
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
            <GeneralInfoCard color={color} title={capability.name} {...props} deleteLabel={"Delete device"}
                             type={HomeComponent.CAPABILITY} message={`Device '${capability.name || ""}'`}
                             notification={`Device '${device.name || ""}'`}
                             nextLabel={"Remove device from room"} handleNext={removeDeviceFromRoom} homeID={homeID}
                             device={device}>
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