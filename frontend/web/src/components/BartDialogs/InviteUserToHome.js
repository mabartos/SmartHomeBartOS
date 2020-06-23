import React, {forwardRef, useImperativeHandle} from "react";
import {useObserver} from "mobx-react-lite";
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";
import {formStyle} from "../../assets/jss/material-dashboard-react/components/BartStyles/formStyle";
import styles from "assets/jss/material-dashboard-react/components/headerLinksStyle.js";
import Search from "@material-ui/icons/Search";
import CustomInput from "../CustomInput/CustomInput";
import useStores from "../../hooks/useStores";
import GridContainer from "../Grid/GridContainer";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import Checkbox from "@material-ui/core/Checkbox";
import List from "@material-ui/core/List";
import avatar from "../../assets/img/avatar.jpeg";
import NoItemsAvailable from "../BartCard/NoItemsAvailable";
import {GeneralModal} from "../Modal/GeneralModal";

const useStyles = makeStyles(formStyle);
const useHeaderStyles = makeStyles(styles);

export const InviteUserToHome = forwardRef((((props, ref) => {
    const classes = useStyles();
    const headerStyles = useHeaderStyles();
    const {userStore, authStore, homeStore} = useStores();
    const {homeID} = props;

    const [open, setOpen] = React.useState(false);

    const [nameOrEmail, setNameOrEmail] = React.useState("");
    const [prevName, setPrevName] = React.useState("prev");

    const [checked, setChecked] = React.useState([]);
    const [isSearched, setIsSearched] = React.useState(false);

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

    React.useEffect(() => {
        if (!open) {
            setIsSearched(false);
            userStore.removeUsers();
        }
    }, [open]);

    return useObserver(() => {
        const {user} = authStore;
        const {users} = userStore;

        const handleChangeName = (event) => {
            setNameOrEmail(event.target.value);
        };

        const handleSearchUser = () => {
            setIsSearched(true);
            if (nameOrEmail !== prevName) {
                userStore.removeUsers();
                userStore.getUserByNameOrEmail(nameOrEmail);
                setPrevName(nameOrEmail);
            }
        };

        const handleSearchKey = (event) => {
            if (event.key === 'Enter') {
                handleSearchUser();
            }
        };

        const handleOpen = (state) => {
            setOpen(state);
        };

        const handleSendInvitation = () => {
            if (checked) {
                [...checked].map((value) => {
                    let invitation = {};
                    invitation.issuerID = user.id;
                    invitation.receiverID = value.id;
                    invitation.homeID = homeID;
                    homeStore.createInvitation(homeID, invitation);
                });
            }
            closeDialog();
        };

        const handleToggle = (value) => () => {
            const currentIndex = checked.indexOf(value);
            const newChecked = [...checked];

            if (currentIndex === -1) {
                newChecked.push(value);
            } else {
                newChecked.splice(currentIndex, 1);
            }
            setChecked(newChecked);
        };

        const getFoundUsers = () => {
            if (users.size > 0) {
                return (
                    <List dense className={classes.root}>
                        {[...users].filter(([k, v]) => v.email !== user.email)
                            .map(([key, value]) => {
                                const labelId = `checkbox-list-secondary-label-${value}`;
                                return (
                                    <ListItem key={value} button onClick={handleToggle(value)}>
                                        <ListItemAvatar>
                                            <Avatar
                                                alt={`Avatar n°${value + 1}`}
                                                src={avatar}
                                            />
                                        </ListItemAvatar>
                                        <ListItemText id={labelId} primary={value.name}
                                                      secondary={value.email}/>
                                        <ListItemSecondaryAction>
                                            <Checkbox
                                                edge="end"
                                                onChange={handleToggle(value)}
                                                checked={checked.indexOf(value) !== -1}
                                                inputProps={{'aria-labelledby': labelId}}
                                            />
                                        </ListItemSecondaryAction>
                                    </ListItem>
                                );
                            })}
                    </List>
                )
            } else {
                return (
                    <NoItemsAvailable/>
                )
            }
        };

        return (
            <GeneralModal open={open} ref={ref} title={"Invite User to Home"} openCallback={handleOpen}
                          confirmCallback={handleSendInvitation} confirmTitle={"Send invitations"}
            >
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
            </GeneralModal>
        )
    });
})));