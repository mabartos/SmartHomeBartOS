import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import GridContainer from "../Grid/GridContainer";
import GridItem from "../Grid/GridItem";
import CustomInput from "../CustomInput/CustomInput";
import CardFooter from "../Card/CardFooter";
import Button from "@material-ui/core/Button";
import CardBody from "../Card/CardBody";
import CardHeader from "../Card/CardHeader";
import Card from "@material-ui/core/Card";
import useStores from "../../hooks/useStores";

const useStyles = makeStyles(theme => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    }
}));

export default function EditForm(props) {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);

    const id = props.ids;
    const userID = id.userID;
    const homeID = id.homeID;
    const roomID = id.roomID;
    const deviceID = id.deviceID;

    const {userStore, homeStore, roomStore, productStore, deviceStore} = useStores();

    const getID = () => {
        if (userID !== undefined) {
            return userID;
        }
        if (homeID !== undefined) {
            if (roomID !== undefined) {
                return userID;
            }
            return homeID;
        }
        if (deviceID !== undefined) {
            return deviceID;
        }
    };

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleUpdate = () => {
        console.log("asdf")
    };

    return (
        <div>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                className={classes.modal}
                open={props.open}
                onClose={handleClose}
                closeAfterTransition
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}
            >
                <Fade in={props.open}>
                    <GridItem xs={12} sm={12} md={4}>
                        <Card>
                            <CardHeader color="primary">
                                <h4 className={classes.cardTitleWhite}>Edit Profile</h4>
                                <h5>#{getID() || "000"}</h5>
                            </CardHeader>
                            <CardBody>
                                <GridContainer>
                                    <GridItem xs={12} sm={12} md={12}>
                                        <CustomInput
                                            labelText={props.name || "name"}
                                            id="username"
                                            formControlProps={{
                                                fullWidth: true
                                            }}
                                        />
                                    </GridItem>
                                </GridContainer>
                            </CardBody>
                            <CardFooter>
                                <Button onClick={() => handleClose()} color="secondary">Cancel</Button>
                                <Button onClick={handleUpdate} color="primary">Update Profile</Button>
                            </CardFooter>
                        </Card>
                    </GridItem>
                </Fade>
            </Modal>
        </div>
    );
};