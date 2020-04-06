import {CircularProgressbar} from "react-circular-progressbar";
import React from "react";

export default function CircularProgress(props) {

    const {value, maxValue, minValue, units} = props;

    return (
        <CircularProgressbar value={value} maxValue={maxValue || 50} minValue={minValue || -20}
                             text={`${value} ${units}`}
                             styles={{
                                 path: {
                                     strokeLinecap: "round",
                                     transition: 'stroke-dashoffset 0.5s ease 0s',
                                     transform: 'rotate(turn)',
                                     transformOrigin: 'center center',
                                 },
                                 trail: {
                                     stroke: '#d6d6d6',
                                     strokeLinecap: 'round',
                                     transform: 'rotate(0.25turn)',
                                     transformOrigin: 'center center',
                                 }
                             }
                             }
        />)
}