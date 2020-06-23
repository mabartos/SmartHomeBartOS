import React, {forwardRef, useImperativeHandle} from "react";
import {useObserver} from "mobx-react-lite";
import {formStyle} from "../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";
import makeStyles from "@material-ui/core/styles/makeStyles";
import CheckedTasks from "../Tasks/CheckedTasks";
import useStores from "../../hooks/useStores";
import {useParams} from "react-router-dom";
import {GeneralModal} from "../Modal/GeneralModal";

const useStyles = makeStyles(formStyle);

export const AddDeviceToRoom = forwardRef(((props, ref) => {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const {homeID} = useParams();
    const {type, roomID} = props;

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
    }, [open, homeStore, homeID]);

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
                deviceStore.addDeviceToRoom(homeID, roomID, value);
            });
        }
        closeDialog();
    };

    const handleOpen = (state) => {
        setOpen(state);
    };

    const handleKeys = (event) => {
        switch (event.key) {
            case 'Enter':
                handleAdd();
                break;
            case 'Escape':
                setOpen(false);
                break;
            default:
                break;
        }
    };

    return useObserver(() => {
        const {devices} = homeStore;

        const getUnassignedDevices = () => {
            let tmp = new Map();
            [...devices].map(([key, value]) => {
                if (value.roomID === -1) {
                    tmp.set(key, value);
                }
            });
            return tmp;
        };
        return (
            <>
                <GeneralModal open={open} openCallback={handleOpen} title={"Add device to room"} confirmCallback={handleAdd}
                              confirmTitle={`Add device`} confirmCondition={getUnassignedDevices().size !== 0}>
                    <CheckedTasks
                        checkedCallback={callbackSetChecked}
                        tasks={getUnassignedDevices()}
                    />
                </GeneralModal>
            </>
        )
    });
}));