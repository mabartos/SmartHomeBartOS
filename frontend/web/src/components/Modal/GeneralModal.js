import React, {forwardRef} from "react";
import makeStyles from "@material-ui/core/styles/makeStyles";
import {formStyle} from "../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";
import {useObserver} from "mobx-react-lite";
import Modal from "@material-ui/core/Modal";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import GridItem from "../Grid/GridItem";
import Card from "../Card/Card";
import CardHeader from "../Card/CardHeader";
import CardBody from "../Card/CardBody";
import CardFooter from "../Card/CardFooter";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles(formStyle);

export const GeneralModal = forwardRef((props, ref) => {
    const classes = useStyles();

    const {
        title, open, children, dismissTitle, confirmTitle, confirmCondition, dismissCallback,
        confirmCallback, handleKeysCallback, hideConfirmTitle, openCallback, color, ...rest
    } = props;

    const handleKeys = (event) => {
        if (handleKeysCallback !== undefined) {
            handleKeysCallback();
        }
        if (event.key === 'Escape') {
            handleDismiss();
        } else if (event.key === 'Enter') {
            handleConfirm();
        }
    };

    const handleDismiss = () => {
        if (dismissCallback !== undefined) {
            dismissCallback();
        }
        handleOpen(false);
    };

    const handleOpen = (state) => {
        if (openCallback !== undefined) {
            openCallback(state);
        }
    };

    const handleConfirm = () => {
        if (confirmCallback !== undefined) {
            confirmCallback();
        }
        handleOpen(false);
    };

    return useObserver(() => {
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
                        <GridItem xs={12} sm={8} md={4}>
                            <Card className={classes.card}>
                                <CardHeader color={color || "info"}>
                                    <h4 className={classes.cardTitleWhite}>{title || "Title"}</h4>
                                </CardHeader>
                                <CardBody>
                                    {children}
                                </CardBody>
                                <CardFooter>
                                    <Button onClick={handleDismiss}
                                            color="secondary">{dismissTitle || "Cancel"}</Button>
                                    {(confirmCondition === undefined || confirmCondition) &&
                                    <Button onClick={handleConfirm}
                                            color="primary"> {confirmTitle || "Confirm"}</Button>}
                                </CardFooter>
                            </Card>
                        </GridItem>
                    </Fade>
                </Modal>
            </div>
        )
    });
});