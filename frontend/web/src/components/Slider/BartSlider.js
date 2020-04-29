import Slider from "@material-ui/core/Slider";
import withStyles from "@material-ui/core/styles/withStyles";
import React from "react";

const CustomSlider = withStyles({
    root: {
        color: '#52af77',
        height: 8,
        marginRight: "15px"
    },
    thumb: {
        height: 33,
        width: 33,
        backgroundColor: '#fff',
        border: '2px solid currentColor',
        marginTop: -8,
        marginLeft: -12,
        '&:focus, &:hover, &$active': {
            boxShadow: 'inherit',
        },
    },
    active: {},
    valueLabel: {
        left: 'calc(-50% + 13px)',
    },
    track: {
        height: 8,
        borderRadius: 4,
    },
    rail: {
        height: 8,
        borderRadius: 4,
    },
})(Slider);

export default function BartSlider(props) {
    const {value, onChange, min, max, step, disabled} = props;

    return (
        <CustomSlider aria-label="pretto slider" defaultValue={value}
                      onChange={onChange}
                      aria-labelledby="discrete-slider"
                      valueLabelDisplay="auto"
                      step={step || 10}
                      marks
                      disabled={disabled}
                      min={min || 10}
                      max={max || 100}/>
    )
}