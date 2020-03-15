import React from "react";
import Notification from "./Notification";
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

export default function SuccessNotification(props) {
    return (
        <Notification message={props.message || "Success"} color={"success"} icon={CheckCircleIcon} close/>
    )
}