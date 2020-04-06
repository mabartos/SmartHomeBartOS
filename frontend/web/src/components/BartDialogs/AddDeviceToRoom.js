import React, {forwardRef, useImperativeHandle} from "react";
import {useObserver} from "mobx-react-lite";
import Backdrop from "@material-ui/core/Backdrop";
import Modal from "@material-ui/core/Modal";
import GridItem from "../Grid/GridItem";
import Fade from "@material-ui/core/Fade";
import {formStyle} from "../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";
import makeStyles from "@material-ui/core/styles/makeStyles";
import CardHeader from "../Card/CardHeader";
import CardBody from "../Card/CardBody";
import CardFooter from "../Card/CardFooter";
import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import CheckedTasks from "../Tasks/CheckedTasks";
import useStores from "../../hooks/useStores";
import {useParams} from "react-router-dom";

const useStyles = makeStyles(formStyle);

export const AddDeviceToRoom = forwardRef(((props, ref) => {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const {homeID} = useParams();

    const [checked, setChecked] = React.useState([]);

    const {homeStore, deviceStore} = useStores();

    React.useEffect(() => {
        if (open) {
            homeStore.getDevicesInHome(homeID);
            const interval = setInterval(() => {
                homeStore.getDevicesInHome(homeID);
            }, 2000);

            return function cleanup() {
                clearInterval(interval);
            }
        }
    }, [open]);

    const callbackSetChecked = (ids) => {
        setChecked(ids);
    };

    const openDialog = () => {
        setOpen(true);
    };

    const closeDialog = () => {
        setOpen(false);
    };

    useImperativeHandle(ref, () => {
        return {
            openDialog: openDialog,
            closeDialog: closeDialog
        }
    });

    const handleAdd = () => {
        if (checked.length !== 0) {
            checked.forEach((value, index) => {
                deviceStore.addDeviceToRoom(homeID, props.roomID, value);
            });
        }
        closeDialog();
    };

    const handleKeys = (event) => {
        switch (event.key) {
            case 'Enter':
                handleAdd();
                break;
            case 'Escape':
                setOpen(false);
                break;
        }
    };

    return useObserver(() => {
        const {devices} = homeStore;

        const getUnassignedDevices = () => {
            let tmp = new Map();
            [...devices].map(([key, value], index) => {
                if (value.roomID === -1) {
                    tmp.set(key, value);
                }
            });
            return tmp;
        };
        return (
            <div>
                <Modal
                    aria-labelledby="transition-modal-title"
                    aria-describedby="transition-modal-description"
                    className={classes.modal}
                    open={open}
                    onKeyDown={handleKeys}
                    closeAfterTransition
                    BackdropComponent={Backdrop}
                    BackdropProps={{
                        timeout: 500,
                    }}
                >
                    <Fade in={open}>
                        <GridItem xs={12} sm={12} md={4}>
                            <Card>
                                <CardHeader color="info">
                                    <h4 className={classes.cardTitleWhite}>Add device to room</h4>
                                </CardHeader>
                                <CardBody>
                                    <CheckedTasks
                                        checkedCallback={callbackSetChecked}
                                        tasks={getUnassignedDevices()}
                                    />
                                </CardBody>
                                <CardFooter>
                                    <Button onClick={closeDialog} color="secondary">Cancel</Button>
                                    {getUnassignedDevices().size !== 0 &&
                                    <Button onClick={handleAdd}
                                            color="primary">Add device {props.type}</Button>
                                    }
                                </CardFooter>
                            </Card>
                        </GridItem>
                    </Fade>
                </Modal>
            </div>
        )
    });
}));