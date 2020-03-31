import React, {forwardRef} from "react";
import useStores from "../../../hooks/useStores";
import {HomeComponent} from "../../../index";
import GridContainer from "../../Grid/GridContainer";
import GridItem from "../../Grid/GridItem";
import CustomInput from "../../CustomInput/CustomInput";
import {AddForm} from "./AddForm";
import {useParams} from "react-router-dom";
import {useObserver} from "mobx-react-lite";

export const AddRoomForm = forwardRef((props, ref) => {
    const {roomStore} = useStores();
    const {homeID} = useParams();

    const [errorName, setErrorName] = React.useState(false);
    const [name, setName] = React.useState("");

    return useObserver(() => {

        const changeName = (event) => {
            const value = event.target.value;
            setErrorName(value.length === 0);
            setName(value);
        };

        const createRoom = () => {
            let room = {};
            if (areValidValues()) {
                room.name = name;
                setName("");
                roomStore.createRoom(homeID, room);
            }
        };

        const areValidValues = () => {
            return name.length !== 0;
        };

        const clearStates = () => {
            setName("");
        };

        return (
            <AddForm ref={ref} type={HomeComponent.ROOM} {...props} color="info" handleAdd={createRoom}
                     areValidValues={areValidValues} clearStates={clearStates}>
                <GridContainer>
                    <GridItem xs={12} sm={12} md={9}>
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
                </GridContainer>
            </AddForm>
        );
    })

});