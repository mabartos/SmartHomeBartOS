import React, {forwardRef, useImperativeHandle} from "react";
import {makeStyles} from "@material-ui/core/styles";
import {formStyle} from "../../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";
import Modal from "@material-ui/core/Modal";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import GridItem from "../../Grid/GridItem";
import Card from "@material-ui/core/Card";
import CardHeader from "../../Card/CardHeader";
import CardBody from "../../Card/CardBody";
import CardFooter from "../../Card/CardFooter";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles(formStyle);

export const AddForm = forwardRef((props, ref) => {
    const classes = useStyles();

    const [open, setOpen] = React.useState(false);

    const openForm = () => {
        setOpen(true);
    };

    const clearStates = () => {
        if (props.clearStates !== undefined) {
            props.clearStates();
        }
    };

    const closeForm = () => {
        clearStates();
        setOpen(false);
    };

    useImperativeHandle(ref, () => {
        return {
            openForm: openForm,
            closeForm: closeForm
        }
    });

    const handleKeys = (event) => {
        switch (event.key) {
            case 'Enter':
                handleClick();
                break;
            case 'Escape':
                closeForm();
                break;
            default:
                break;
        }
    };

    const handleClick = (event) => {
        if (props.handleAdd !== undefined) {
            props.handleAdd();
        }
        if (props.areValidValues()) {
            closeForm();
        }
        clearStates();
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
                            <CardHeader color={props.color || "success"}>
                                <h4 className={classes.cardTitleWhite}>Add {props.type}</h4>
                            </CardHeader>
                            <CardBody>
                                {props.children}
                            </CardBody>
                            <CardFooter>
                                <Button onClick={closeForm} color="secondary">Cancel</Button>
                                <Button onClick={handleClick}
                                        color="primary">Add {props.type}</Button>
                            </CardFooter>
                        </Card>
                    </GridItem>
                </Fade>
            </Modal>
        </div>
    );

});