import React, {forwardRef, useImperativeHandle} from "react";
import GridItem from "../../Grid/GridItem";
import GridContainer from "../../Grid/GridContainer";
import {BROKER_URL_REGEX} from "../../../index";
import useStores from "../../../hooks/useStores";
import EmailIcon from '@material-ui/icons/Email';
import Tasks from "../../Tasks/Tasks";
import CustomTabs from "../../CustomTabs/CustomTabs";
import Modal from "@material-ui/core/Modal";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import {makeStyles} from "@material-ui/core/styles";
import {formStyle} from "../../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";
import Button from "@material-ui/core/Button";
import CustomInput from "../../CustomInput/CustomInput";
import HomeIcon from '@material-ui/icons/Home';
import {useObserver} from "mobx-react-lite";

const useStyles = makeStyles(formStyle);

export const AddHomeForm = forwardRef((props, ref) => {
    const {homeStore, userStore} = useStores();
    const classes = useStyles();

    const [open, setOpen] = React.useState(false);

    const [name, setName] = React.useState("");
    const [brokerURL, setBrokerURL] = React.useState("");

    const [errorName, setErrorName] = React.useState(false);
    const [errorBrokerURL, setErrorBrokerURL] = React.useState(false);

    const [checked, setChecked] = React.useState([]);
    const [invitationsActive, setInvitationsActive] = React.useState(false);

    React.useEffect(() => {
        const interval = setInterval(() => {
            userStore.reloadInvitations();
        }, 2000);
        return () => clearInterval(interval);
    }, []);

    const changeName = (event) => {
        const value = event.target.value;
        setErrorName(value.length === 0);
        setName(value);
    };

    const validateBrokerURL = (value) => {
        const patt = new RegExp(BROKER_URL_REGEX);
        return (value.length !== 0) ? patt.test(value) : true;
    };

    const changeBrokerURL = (event) => {
        const value = event.target.value;
        setErrorBrokerURL(!validateBrokerURL(value));
        setBrokerURL(value);
    };

    const createHome = () => {
        if (invitationsActive) {
            checked.forEach((val) => {
                userStore.acceptInvitation(val);
            });
        } else {
            if (areValidValues()) {
                let home = {};
                home.name = name;
                home.brokerURL = brokerURL;
                homeStore.createHome(home);
            }
        }
        closeForm();
    };

    const clearForms = () => {
        setName("");
        setBrokerURL("");
    };

    const areValidValues = () => {
        return !errorName && !errorBrokerURL && name.length !== 0;
    };

// MODAL
    const handleKeys = (event) => {
        switch (event.key) {
            case 'Enter':
                createHome();
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

    const openForm = () => {
        setOpen(true);
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

    return useObserver(() => {
        const {invitations} = userStore;

        const handleAccept = (id) => {
            userStore.acceptInvitation(id);
        };

        const handleDismiss = (id) => {
            userStore.dismissInvitation(id);
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
                        <GridContainer>
                            <GridItem xs={12} sm={12} md={12}>
                                <CustomTabs
                                    headerColor="primary"
                                    tabs={[
                                        {
                                            tabName: "Create",
                                            tabIcon: HomeIcon,
                                            tabContent: (
                                                <div>
                                                    <GridItem xs={12} sm={12} md={12}>
                                                        <CustomInput
                                                            labelText={"Name"}
                                                            id="name"
                                                            error={errorName}
                                                            onChange={changeName}
                                                            formControlProps={{
                                                                fullWidth: true
                                                            }}
                                                        />
                                                    </GridItem>
                                                    <GridItem xs={12} sm={12} md={12}>
                                                        <CustomInput
                                                            labelText={"Broker URL"}
                                                            id="url"
                                                            onChange={changeBrokerURL}
                                                            error={errorBrokerURL}
                                                            formControlProps={{
                                                                fullWidth: true
                                                            }}
                                                        />
                                                    </GridItem>
                                                </div>

                                            )
                                        },
                                        {
                                            tabName: "Invitations",
                                            tabIcon: EmailIcon,
                                            tabContent: (
                                                <Tasks
                                                    tasks={invitations||[]}
                                                    checkedCallback={setChecked}
                                                    hideCheckBox
                                                    activeCallback={setInvitationsActive}
                                                    handleAccept={handleAccept}
                                                    handleDismiss={handleDismiss}
                                                />
                                            )
                                        },
                                    ]}
                                >
                                    <Button onClick={closeForm} color="secondary">Cancel</Button>
                                    {(!invitationsActive || invitations.size > 0) &&
                                    <Button onClick={createHome} color="primary">Add Home</Button>
                                    }
                                </CustomTabs>
                            </GridItem>
                        </GridContainer>
                    </Fade>
                </Modal>
            </div>
        )
    });
});