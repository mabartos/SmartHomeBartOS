import React from "react";
import {useObserver} from "mobx-react-lite";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import GeneralService from "../../../services/GeneralService";
import {Typography} from "@material-ui/core";
import makeStyles from "@material-ui/core/styles/makeStyles";
import BartStateButton from "../BartStateButton";
import BartSlider from "../../Slider/BartSlider";

const useStyles = makeStyles({
    root: {
        position: "relative",
        padding: "5px",
    },
    intensityContainer: {
        position: "relative",
        paddingTop: "10px !important",
        marginTop: "10px !important",
    },
    intensityTitle: {
        textAlign: "center",
        paddingBottom: "10px"
    },
    sliderContainer: {
        position: "relative",
        width: "100%",
        padding: "0 !important",
        margin: "0 !important",
        marginTop: "10px",
        justifyContent: "center",
        alignContent: "center",
    },
    slider: {
        transform: [{scaleX: 1.5}, {scaleY: 1.5}]
    }
});

export default function LightsCapCard(props) {
    const classes = useStyles();

    const {capability, homeID, roomID, mqtt} = props;
    const id = capability.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.LIGHT.topic, id);

    const [isTurnedOn, setIsTurnedOn] = React.useState(capability.isTurnedOn);
    const [intensity, setIntensity] = React.useState(capability.intensity);
    const [minIntensity, setMinIntensity] = React.useState(capability.minIntensity);

    const [data, setData] = React.useState(capability);

    React.useEffect(() => {
        if (props.data.topic === topic) {
            const object = GeneralService.getObjectFromString(props.data.payloadString);
            if (object !== null) {
                setData(object);
                setIsTurnedOn(object.isTurnedOn);
                setIntensity(object.intensity);
                setMinIntensity(object.minIntensity);
            }
        }
    }, [props.data]);

    let timeout;
    const handleChangeIntensity = (event, newValue) => {
        timeout && clearTimeout(timeout);
        timeout = setTimeout(() => {
            let result = data;
            setIntensity(newValue);
            result.intensity = newValue;
            mqtt.sendWithParams(topic, result, 0, true);
        }, 600)
    };

    return useObserver(() => {

        const handleChangeState = (state) => {
            const isTurnedOn = state;
            setIsTurnedOn(isTurnedOn);
            let result = {};
            result.isTurnedOn = isTurnedOn;
            result.intensity = intensity;
            result.minIntensity = minIntensity;
            mqtt.send(topic, result);
        };

        return (
            <>
                <div className={classes.root}>
                    <BartStateButton isTurnedOn={isTurnedOn} onChange={handleChangeState}/>
                    <div className={classes.intensityContainer}>
                        <Typography className={classes.intensityTitle}>
                            Intensity
                        </Typography>
                        <div className={classes.sliderContainer}>
                            <BartSlider value={intensity} onChange={handleChangeIntensity}/>
                        </div>
                    </div>
                </div>
            </>
        )
    });
}