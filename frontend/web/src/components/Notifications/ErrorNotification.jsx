import React from "react";
import Notification from "./Notification";
import ErrorIcon from '@material-ui/icons/Error';

export default function ErrorNotification(props) {
    const [message, setMessage] = React.useState(props.message);

    React.useEffect(() => {
        if (message === "Failed to fetch") {
            setMessage("Server is not available.");
        }
    }, []);

    return (
        <div>
            <Notification message={message} color={"danger"} icon={ErrorIcon}/>
        </div>
    );
}