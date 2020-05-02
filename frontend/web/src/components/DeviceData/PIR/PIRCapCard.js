import DeviceService from "../../../services/homeComponent/DeviceService";
import {CapabilityType} from "../../../constants/Capabilities";
import React from "react";
import GeneralService from "../../../services/GeneralService";
import {makeStyles, Typography} from "@material-ui/core";
import classNames from "classnames";

const useStyles = makeStyles(style => ({
    message: {
        fontSize:"1.7em"
    },
    occupied: {
        color: "rgb(182, 96, 100)"
    },
    notOccupied: {
        color: "rgb(96, 182, 100)"
    }
}));

export default function PIRCapCard(props) {
    const {capability, homeID, roomID, data} = props;
    const classes = useStyles();

    const [isTurnedOn, setIsTurnedOn] = React.useState(capability.isTurnedOn);

    const id = capability.id;
    const topic = DeviceService.getFullTopic(homeID, roomID, CapabilityType.PIR.topic, id);

    const allClasses = classNames({
        [classes.message]:true,
        [classes.occupied]:isTurnedOn,
        [classes.notOccupied]:!isTurnedOn
    });

    React.useEffect(() => {
        if (data.topic === topic) {
            const object = GeneralService.getObjectFromString(data.payloadString);
            if (object !== null) {
                setIsTurnedOn(object.isTurnedOn);
            }
        }
    }, [data]);

    const getState = () => {
        return (isTurnedOn) ? (
            <Typography className={allClasses}>
                Movements detected
            </Typography>
        ) : (
            <Typography className={allClasses}>
                No movements
            </Typography>
        );
    };

    return (
        <>
            {getState()}
        </>
    )
}