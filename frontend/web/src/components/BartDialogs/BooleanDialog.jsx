import React from "react";
import Slide from "@material-ui/core/Slide";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import useStores from "../../hooks/useStores";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default function BooleanDialog(props) {
    const id=props.ids;
    const userID = id.userID;
    const homeID = id.homeID;
    const roomID = id.roomID;
    const deviceID = id.deviceID;

    const {userStore, homeStore, roomStore, productStore, deviceStore} = useStores();

    const deleteByID = () => {
        console.log(homeID);
        if (userID !== undefined) {
            console.log("here");
            userStore.deleteUser(userID);
            return;
        }
        if (homeID !== undefined) {
            if (roomID !== undefined) {
                roomStore.deleteRoom(homeID, roomID);
                return;
            }
            homeStore.deleteHome(homeID);
            return;
        }
        if (deviceID !== undefined) {
        }
    };

    const title = props.title || "Are you sure to delete item?";

    const handleNo = () => {
    };

    const handleYes = () => {
        deleteByID();
    };

    return (
        <Dialog
            open={props.open}
            TransitionComponent={Transition}
            keepMounted
            aria-labelledby="alert-dialog-slide-title"
            aria-describedby="alert-dialog-slide-description"
        >
            <DialogTitle id="alert-dialog-slide-title">{title}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-slide-description">
                    {props.message}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleNo} color="primary">
                    No
                </Button>
                <Button onClick={handleYes} color="primary">
                    Yes
                </Button>
            </DialogActions>
        </Dialog>
    );
}