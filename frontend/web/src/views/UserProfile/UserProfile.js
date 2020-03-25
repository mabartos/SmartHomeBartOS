import React from "react";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
// core components
import GridItem from "components/Grid/GridItem.js";
import GridContainer from "components/Grid/GridContainer.js";
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardAvatar from "components/Card/CardAvatar.js";
import CardBody from "components/Card/CardBody.js";

import avatar from "assets/img/faces/marc.jpg";
import useStores from "../../hooks/useStores";
import {useObserver} from "mobx-react-lite";

const styles = {
    cardCategoryWhite: {
        color: "rgba(255,255,255,.62)",
        margin: "0",
        fontSize: "14px",
        marginTop: "0",
        marginBottom: "0"
    },
    cardTitleWhite: {
        color: "#FFFFFF",
        marginTop: "0px",
        minHeight: "auto",
        fontWeight: "300",
        fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
        marginBottom: "3px",
        textDecoration: "none"
    }
};

const useStyles = makeStyles(styles);

export default function UserProfile() {
    const classes = useStyles();
    const {authStore} = useStores();

    return useObserver(() => {
        const {user} = authStore;

        const onSelect = () => {
          authStore.test();
        };

        return (
            <div>
                <GridContainer justify={"center"}>
                    <GridItem xs={12} sm={12} md={6}>
                        <Card profile>
                            <CardAvatar profile>
                                <a href="#pablo" onClick={e => e.preventDefault()}>
                                    <img src={avatar} alt="..."/>
                                </a>
                            </CardAvatar>
                            <CardBody profile>
                                <h4>{user.name}</h4>
                                <h5>ID: {user.sub}</h5>
                                <h5>Email: {user.email}</h5>

                                <p>{authStore.getToken()}</p>

                                {/* <p className={classes.description}>
                                    Don{"'"}t be scared of the truth because we need to restart the
                                    human foundation in truth And I love you like Kanye loves Kanye
                                    I love Rick Owens’ bed design but the back is...
                                </p>*/}
                                <Button color="primary" round>
                                    EDIT
                                </Button>
                            </CardBody>
                        </Card>
                    </GridItem>
                </GridContainer>
            </div>
        );
    });
}
