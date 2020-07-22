import React, {forwardRef, useImperativeHandle} from "react";
import {useObserver} from "mobx-react-lite";
import {GeneralModal} from "../../components/Modal/GeneralModal";
import useStores from "../../hooks/useStores";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import avatar from "../../assets/img/avatar.jpeg";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import makeStyles from "@material-ui/core/styles/makeStyles";
import {getRole, Role} from "../../constants/Role";
import IconButton from "@material-ui/core/IconButton";

const useStyles = makeStyles(() => ({
    root: {
        width: '100%',
    },
    iconButton: {
        width: "100%",
        minWidth: "120px"
    },
    disabledButton: {
        color: "black !important"
    }
}));

export const HomeMembersModal = forwardRef((props, ref) => {
    const [open, setOpen] = React.useState(false);
    const classes = useStyles();

    const {homeStore, uiStore} = useStores();

    const openDialog = () => {
        setOpen(true);
    };

    const closeDialog = () => {
        setOpen(false);
    };

    const handleOpenState = (state) => {
        state ? openDialog() : closeDialog();
    };

    React.useEffect(() => {
        if (open) {
            homeStore.getMembers(uiStore.homeID);
        }
    }, [open, homeStore, uiStore]);

    useImperativeHandle(ref, () => {
        return {
            openDialog: openDialog,
            closeDialog: closeDialog
        }
    });

    return useObserver(() => {
        const {members} = homeStore;

        const getMembers = () => {
            return (
                <List className={classes.root}>
                    {[...members].map(([key, value]) => {
                        const labelId = `checkbox-list-secondary-label-${value}`;
                        const role = getRole(value.role);
                        return (
                            <ListItem key={value}>
                                <ListItemAvatar>
                                    <Avatar
                                        alt={`Avatar n°${value + 1}`}
                                        src={avatar}
                                    />
                                </ListItemAvatar>
                                <ListItemText id={labelId} primary={value.user.name}
                                              secondary={value.user.email}/>
                                <ListItemSecondaryAction>
                                    <IconButton
                                        className={classes.iconButton}
                                        key="icon"
                                        aria-label="Role"
                                        color="inherit"
                                        disabled={role !== Role.ADMIN}
                                        classes={{disabled: classes.disabledButton}}
                                    >
                                        <ListItemText primary={role.name}/>
                                        <role.icon/>
                                    </IconButton>
                                </ListItemSecondaryAction>
                            </ListItem>
                        );
                    })}
                </List>
            )
        };

        return (
            <>
                <GeneralModal open={open} openCallback={handleOpenState} title={"Home Members"}
                              confirmCondition={false}>
                    {members && getMembers()}
                </GeneralModal>
            </>
        )
    });
});