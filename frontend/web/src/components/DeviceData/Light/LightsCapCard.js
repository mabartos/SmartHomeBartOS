import React from "react";
import {useObserver} from "mobx-react-lite";
import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import GeneralService from "../../../services/GeneralService";
import GridItem from "../../Grid/GridItem";
import Slider from "@material-ui/core/Slider";
import {Typography} from "@material-ui/core";
import makeStyles from "@material-ui/core/styles/makeStyles";
import GridContainer from "../../Grid/GridContainer";
import BartStateButton from "../BartStateButton";

const useStyles = makeStyles({
    root: {
        height: 100
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
            mqtt.send(topic, result);
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
                <BartStateButton isTurnedOn={isTurnedOn} onChange={handleChangeState}/>
                <GridItem xs={12} sm={12} md={12}>
                    <br/>
                    {intensity}
                    <br/>
                    {minIntensity}
                    <br/>
                    {id}
                    <br/>
                    <Typography id="discrete-slider">
                        Intensity
                    </Typography>

                    <GridContainer>
                        <GridItem xs={12} sm={6} md={3}>
                            <div className={classes.root}>
                                <Slider
                                    orientation="vertical"
                                    defaultValue={intensity}
                                    onChange={handleChangeIntensity}
                                    aria-labelledby="discrete-slider"
                                    valueLabelDisplay="auto"
                                    step={10}
                                    marks
                                    min={0}
                                    max={100}
                                />
                            </div>
                        </GridItem>
                        <GridItem xs={12} sm={6} md={3}>
                            {/*<FormControl component={"fieldset"}>
                            <FormGroup>
                                <FormLabel>Settings</FormLabel>
                                <FormControlLabel control={<Switch name={"state"} onChange={handleChangeState}/>}
                                                  label={isTurnedOn}/>
                            </FormGroup>
                        </FormControl>*/}
                        </GridItem>
                    </GridContainer>

                </GridItem>
            </>
        )
    });
}