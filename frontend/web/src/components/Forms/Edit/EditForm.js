import React, {forwardRef, useImperativeHandle} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import GridItem from "../../Grid/GridItem";
import CardFooter from "../../Card/CardFooter";
import Button from "@material-ui/core/Button";
import CardBody from "../../Card/CardBody";
import CardHeader from "../../Card/CardHeader";
import Card from "@material-ui/core/Card";
import {HomeComponent} from "../../../index"
import {formStyle} from "../../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";

const useStyles = makeStyles(formStyle);

export const EditForm = forwardRef((props, ref) => {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);

    const openForm = () => {
        setOpen(true);
    };

    const closeForm = () => {
        clearStates();
        setOpen(false);
    };

    const handleKeys = (event) => {
        switch (event.key) {
            case 'Enter':
                handleUpdate();
                break;
            case 'Escape':
                closeForm();
                break;
        }
    };

    const clearStates = () => {
        if (props.clearStates !== undefined) {
            props.clearStates();
        }
    };

    const handleUpdate = () => {
        if (props.handleUpdate !== undefined) {
            props.handleUpdate();
            clearStates();
        }
        if (props.areValidValues())
            closeForm();
    };

    useImperativeHandle(ref, () => {
        return {
            openForm: openForm,
            closeForm: closeForm
        }
    });

    const getID = () => {
        switch (props.type) {
            case HomeComponent.USER:
                if (props.userID !== undefined) {
                    return props.userID;
                }
                break;
            case HomeComponent.HOME:
                if (props.homeID !== undefined) {
                    return props.homeID;
                }
                break;
            case HomeComponent.ROOM:
                if (props.roomID !== undefined) {
                    return props.roomID;
                }
                break;
            case HomeComponent.DEVICE:
                if (props.deviceID !== undefined) {
                    return props.deviceID;
                }
                break;
        }
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
                            <CardHeader color="primary">
                                <h4 className={classes.cardTitleWhite}>Edit {props.type} - '{props.title}'</h4>
                                <h5>#{getID() || "000"}</h5>
                            </CardHeader>
                            <CardBody>
                                {props.children}
                            </CardBody>
                            <CardFooter>
                                <Button onClick={closeForm} color="secondary">Cancel</Button>
                                <Button onClick={handleUpdate}
                                        color="primary">Update {props.type}</Button>
                            </CardFooter>
                        </Card>
                    </GridItem>
                </Fade>
            </Modal>
        </div>
    );
});