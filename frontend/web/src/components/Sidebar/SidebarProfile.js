import {useObserver} from "mobx-react-lite";
import GridItem from "../Grid/GridItem";
import {Clickable} from "react-clickable";
import EditIcon from "@material-ui/icons/Edit";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";
import Grid from "@material-ui/core/Grid";
import React from "react";
import {makeStyles} from "@material-ui/core/styles";
import avatar from "assets/img/faces/marc.jpg";
import useStores from "../../hooks/useStores";
import {useHistory} from "react-router-dom";

const useProfileStyles = makeStyles(style => ({
    container: {
        zIndex: 50,
        color: "white",
        direction: "row",
        justify: "center",
        alignItems: "center",
        alignContent: "center",
        overflowX: "hidden",
        height: "220px",
    },
    items: {
        display: "grid",
        gridTemplateColumns: "repeat(3,100px)",
        alignItems: "center",
        justifyContent: "center",
        alignContent: "center",
        overflow: "hidden",
        padding: "0 5px 0 20px"
    },
    img: {
        maxWidth: "65px",
        maxHeight: "65px",
        borderRadius: "15px !important",
        padding: "5px",
        paddingLeft: "10px"
    },
    icon: {
        minWidth: "20px",
        minHeight: "20px",
    },
    name: {
        overflow: 'hidden',
        fontSize: "20px"
    },
    manageContainer: {
        display: "grid",
        gridTemplateColumns: "repeat(2, 3fr)",
        justify: "center",
        alignItems: "center",
        alignContent: "center",
        spacing: 3,
        position: "relative",
        width: "100%",
        padding: "10px 20px 0 20px"

    },
    edit: {
        textAlign: "center",
        gridCol: 1
    },
    logout: {
        textAlign: "center",
        gridCol: 2
    }
}));

export default function SidebarProfile(props) {
    const profileStyle = useProfileStyles();
    const {authStore} = useStores();
    const history = useHistory();

    React.useEffect(() => {
        authStore.getUserInfo();
    });

    return useObserver(() => {
        const {user, isAuthenticated} = authStore;

        const handleLogout = () => {
            authStore.logout();
        };

        const handleEdit = () => {
            authStore.edit();
        };

        const handleRedirectProfile = () => {
            history.push("/admin/user");
        };

        const upperCase = (name) => {
            if (typeof name !== 'string') return "";
            return name.charAt(0).toUpperCase() + name.slice(1);
        };

        if (isAuthenticated) {
            return (
                <Grid container className={profileStyle.container}>
                    <div className={profileStyle.items}>
                        <GridItem xs>
                            <Clickable onClick={handleRedirectProfile}>
                                <img className={profileStyle.img} src={avatar} alt={"profile image"}/>
                            </Clickable>
                        </GridItem>
                        <GridItem xs>
                            <div className={profileStyle.name}>
                                {user && upperCase(user.given_name)}
                                <br/>
                                {user && upperCase(user.family_name)}
                            </div>
                        </GridItem>
                    </div>

                    <hr style={{width: "80%"}}/>

                    <div className={profileStyle.manageContainer}>
                        <div className={profileStyle.edit}>
                            <Clickable onClick={handleEdit}>
                                <EditIcon/>
                                <br/>
                                Edit
                            </Clickable>
                        </div>
                        <div className={profileStyle.logout}>
                            <Clickable onClick={handleLogout}>
                                <ExitToAppIcon/>
                                <br/>
                                Logout
                            </Clickable>
                        </div>
                    </div>
                </Grid>
            );
        }
    });
}