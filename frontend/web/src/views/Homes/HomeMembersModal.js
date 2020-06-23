import React, {forwardRef, useImperativeHandle} from "react";
import {useObserver} from "mobx-react-lite";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import GridItem from "../../components/Grid/GridItem";
import Card from "@material-ui/core/Card";
import CardHeader from "../../components/Card/CardHeader";
import CardBody from "../../components/Card/CardBody";
import CustomInput from "../../components/CustomInput/CustomInput";
import Button from "@material-ui/core/Button";
import Search from "@material-ui/icons/Search";
import GridContainer from "../../components/Grid/GridContainer";
import CardFooter from "../../components/Card/CardFooter";
import Modal from "@material-ui/core/Modal";

export const HomeMembersModal = forwardRef((props, ref) => {
    const [open, setOpen] = React.useState(false);

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


    return useObserver(() => {


        return (
            <>
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
                                <CardHeader color={"info"}>
                                    <h4 className={classes.cardTitleWhite}>Invite User to Home</h4>
                                </CardHeader>
                                <CardBody>
                                    <div className={headerStyles.searchWrapper}>
                                        <CustomInput
                                            formControlProps={{
                                                className: headerStyles.margin + " " + headerStyles.search
                                            }}
                                            inputProps={{
                                                placeholder: "Find user",
                                                inputProps: {
                                                    "aria-label": "Find user"
                                                }
                                            }}
                                            onChange={handleChangeName}
                                            onKeyDown={handleSearchKey}
                                        />
                                        <Button color="primary" aria-label="edit" onClick={handleSearchUser}>
                                            <Search/>
                                        </Button>
                                    </div>
                                    <GridContainer>
                                        {isSearched && getFoundUsers()}
                                    </GridContainer>

                                </CardBody>
                                <CardFooter>
                                    <Button onClick={closeDialog} color="secondary">Cancel</Button>
                                    <Button onClick={handleSendInvitation} color="primary"> Send invitation
                                        card</Button>
                                </CardFooter>
                            </Card>
                        </GridItem>
                    </Fade>
                </Modal>
            </>
        )
    });
});