import React, {forwardRef, useImperativeHandle} from "react";
import {useObserver} from "mobx-react-lite";
import Modal from "@material-ui/core/Modal";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import GridItem from "../Grid/GridItem";
import Card from "@material-ui/core/Card";
import CardHeader from "../Card/CardHeader";
import CardBody from "../Card/CardBody";
import CardFooter from "../Card/CardFooter";
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";
import {formStyle} from "../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";
import styles from "assets/jss/material-dashboard-react/components/headerLinksStyle.js";
import Search from "@material-ui/icons/Search";
import CustomInput from "../CustomInput/CustomInput";
import useStores from "../../hooks/useStores";
import GridContainer from "../Grid/GridContainer";
import NoItemsAvailable from "../BartCard/NoItemsAvailable";

const useStyles = makeStyles(formStyle);
const useHeaderStyles = makeStyles(styles);

export const InviteUserToHome = forwardRef((((props, ref) => {
    const classes = useStyles();
    const headerStyles = useHeaderStyles();
    const {userStore, authStore, homeStore} = useStores();
    const {homeID} = props;

    const [open, setOpen] = React.useState(false);
    const [userName, setUserName] = React.useState("");
    const [foundUser, setFoundUser] = React.useState(null);

    const openDialog = () => {
        setOpen(true);
    };

    const closeDialog = () => {
        setOpen(false);
    };

    const handleKeys = () => {

    };

    useImperativeHandle(ref, () => {
        return {
            openDialog: openDialog,
            closeDialog: closeDialog
        }
    });

    //TODO
    React.useEffect(() => {
        [...userStore.users].map(([key, item], index) => {
            if (foundUser === null) {
                setFoundUser(item);
            }
        });
    });

    return useObserver(() => {
        const {user} = authStore;

        const handleChangeName = (event) => {
            setUserName(event.target.value);
        };

        const handleSearchUser = () => {
            userStore.removeUsers();
            userStore.getUserByUsername(userName);
        };

        const handleSendInvitation = () => {
            if (foundUser !== null && homeID) {
                let invitation = {};
                invitation.issuerID = user.id;
                invitation.receiverID = foundUser.id;
                invitation.homeID = homeID;
                homeStore.createInvitation(homeID, invitation);
            }
            closeDialog();
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
                                    <h4 className={classes.cardTitleWhite}>Invite User to Home</h4>
                                </CardHeader>
                                <CardBody>
                                    <div className={headerStyles.searchWrapper}>
                                        <CustomInput
                                            formControlProps={{
                                                className: headerStyles.margin + " " + headerStyles.search
                                            }}
                                            inputProps={{
                                                placeholder: "Search user",
                                                inputProps: {
                                                    "aria-label": "Search user"
                                                }
                                            }}
                                            onChange={handleChangeName}
                                        />
                                        <Button color="primary" aria-label="edit" onClick={handleSearchUser}>
                                            <Search/>
                                        </Button>
                                    </div>
                                    <GridContainer>
                                        {foundUser !== null &&
                                        <NoItemsAvailable message={`User '${foundUser.name}' found`}/>}
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
            </div>
        )
    });
})));