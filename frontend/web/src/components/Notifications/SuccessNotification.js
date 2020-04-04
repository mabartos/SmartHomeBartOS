import React from "react";
import Notification from "./Notification";
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

export default function SuccessNotification(props) {

    const [message, setMessage] = React.useState("");

    React.useEffect(() => {
        setMessage(props.message);
        return function cleanup() {
            setMessage("");
        }
    }, [props.message]);


    return (
        <Notification message={message || "Success"} color={"success"} icon={CheckCircleIcon} close/>
    )
}