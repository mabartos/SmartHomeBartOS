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

    const [isTurnedOn, setIsTurnedOn] = React.useState(props.isTurnedOn || false);

    const handleChange = () => {
        setIsTurnedOn(!isTurnedOn);
        if (props.onChange !== undefined) {
            props.onChange(isTurnedOn);
        }
    };

    let buttonClasses = classNames({
        [classes.button]: true,
        [classes.active]: isTurnedOn,
        [classes.inActive]: !isTurnedOn
    });

    const getButton = () => {
        return (isTurnedOn) ?
            (<Button variant="contained" color="primary" block className={buttonClasses} onClick={handleChange}>
                    ON
                </Button>
            ) :
            <Button variant="outlined" color="secondary" block className={buttonClasses}
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