import React, {useEffect} from "react";
import GridItem from "../Grid/GridItem";
import GridContainer from "../Grid/GridContainer";
import AdjustIcon from '@material-ui/icons/Adjust';
import Snackbar from "../Snackbar/Snackbar";
import {SemipolarLoading} from "react-loadingg";

export default function Notification(props) {
    const [open, setOpen] = React.useState(false);
    const [showLoading,setShowLoading]=React.useState(props.showLoading);

    useEffect(() => {
        console.log("show");
        console.log(props.showLoading)
        setOpen(true);
        if (props.close) (
            setTimeout(() => {
                setOpen(false);
            }, 5000)
        )
    }, []);

    const closeNotification = () => {
        setOpen(false);
    };

    return (
        <GridContainer justify="center">
            <GridItem xs={12} sm={12} md={6}>
                <Snackbar
                    message={props.message}
                    color={props.color || "info"}
                    icon={props.icon || AdjustIcon}
                    place="tc"
                    closeNotification={props.closeNotification || closeNotification}
                    close={props.close}
                    open={open}
                />
            </GridItem>
            {props.showLoading!==false && open && props.color === "danger" && <SemipolarLoading/>}
        </GridContainer>
    );
}