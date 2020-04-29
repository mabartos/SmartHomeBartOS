import React from "react";
import {makeStyles} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import classNames from "classnames";


const useStyle = makeStyles(style => ({
    button: {
        width: "80%",
        marginLeft: "0",
        minHeight: "60px",
        fontSize: "2em"
    },
    icon: {
        textAlign: "center",
        justifyContent: "center",
        alignContent: "center",
        alignItems: "center",
        color: "white",
        fontSize: "20px"
    },
    active: {
        backgroundColor: "rgb(96, 182, 100)",
        "&:hover": {
            transition: "0.4s",
            backgroundColor: "rgba(96, 182, 100,0.9)"
        }
    }

}));

export default function BartStateButton(props) {
    const classes = useStyle();
    const {disabled} = props;

    const [isTurnedOn, setIsTurnedOn] = React.useState(props.isTurnedOn || false);

    const handleChange = () => {
        let state = !isTurnedOn;
        setIsTurnedOn(!isTurnedOn);
        if (props.onChange !== undefined) {
            props.onChange(state);
        }
    };

    let buttonClasses = classNames({
        [classes.button]: true,
        [classes.active]: isTurnedOn,
    });

    const getButton = () => {
        return (isTurnedOn) ?
            (<Button variant="contained" color="primary" disabled={disabled} block className={buttonClasses}
                     onClick={handleChange}>
                    ON
                </Button>
            ) :
            <Button variant="outlined" color="secondary" block disabled={disabled} className={buttonClasses}
                    onClick={handleChange}>
                OFF
            </Button>;
    };

    return (
        <>
            {getButton()}
        </>
    );

}