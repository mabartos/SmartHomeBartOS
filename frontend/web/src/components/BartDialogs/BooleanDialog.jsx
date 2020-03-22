import React, {forwardRef, useImperativeHandle} from "react";
import Slide from "@material-ui/core/Slide";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import useStores from "../../hooks/useStores";
import {HomeComponent} from "../../index";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export const BooleanDialog = forwardRef((props, ref) => {
    const userID = props.userID;
    const homeID = props.homeID;
    const roomID = props.roomID;
    const deviceID = props.deviceID;

    const [open, setOpen] = React.useState(false);

    const {userStore, homeStore, roomStore, productStore, deviceStore} = useStores();

    const closeDialog = () => {
        setOpen(false);
    };

    const openDialog = () => {
        setOpen(true);
    };

    useImperativeHandle(ref, () => {
        return {
            closeDialog: closeDialog,
            openDialog: openDialog
        }
    });

    const deleteByID = () => {
        switch (props.type) {
            case HomeComponent.USER:
                if (userID !== undefined) {
                    userStore.deleteUser(userID);
                    return;
                }
                break;
            case HomeComponent.HOME:
                if (homeID !== undefined) {
                    homeStore.deleteHome(homeID);
                    return;
                }
                break;
            case HomeComponent.ROOM:
                if (roomID !== undefined) {
                    roomStore.deleteRoom(homeID, roomID);
                    return;
                }
                break;
            case HomeComponent.DEVICE:
                if (deviceID !== undefined) {
                    return deviceID;
                }
                break;
        }
    };

    const title = "Are you sure to delete item?";

    const handleYes = () => {
        deleteByID();
        closeDialog();
    };

    return (
        <Dialog
            open={open}
            TransitionComponent={Transition}
            keepMounted
            aria-labelledby="alert-dialog-slide-title"
            aria-describedby="alert-dialog-slide-description"
        >
            <DialogTitle id="alert-dialog-slide-title">{title}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-slide-description">
                    {props.message || "Home '" + props.title + "'"}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={closeDialog} color="primary">
                    No
                </Button>
                <Button onClick={handleYes} color="primary">
                    Yes
                </Button>
            </DialogActions>
        </Dialog>
    );
});