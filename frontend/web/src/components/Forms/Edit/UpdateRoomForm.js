import React, {forwardRef} from "react";
import useStores from "../../../hooks/useStores";
import {EditForm} from "./EditForm";
import {HomeComponent} from "../../../index";
import GridItem from "../../Grid/GridItem";
import CustomInput from "../../CustomInput/CustomInput";
import {useParams} from "react-router-dom";


export const UpdateRoomForm = forwardRef((props, ref) => {
    const [name, setName] = React.useState("");
    const [errorName, setErrorName] = React.useState(false);
    const {homeID} = useParams();

    const {roomStore} = useStores();

    const handleUpdate = () => {
        if (areValidValues() && props.roomID !== undefined) {
            let room = {};
            room.name = name;
            roomStore.updateRoom(homeID, props.roomID, room);
        }
    };

    const areValidValues = () => {
        return (!errorName && name.length !== 0);
    };

    const changeName = (event) => {
        const value = event.target.value;
        setErrorName(value.length === 0);
        setName(value);
    };

    const clearStates = () => {
        setName("");
    };

    return (
        <EditForm ref={ref} type={HomeComponent.ROOM} {...props} handleUpdate={handleUpdate}
                  areValidValues={areValidValues} clearStates={clearStates}>
            <GridItem xs={12} sm={12} md={9}>
                <CustomInput
                    labelText={"Name"}
                    id="username"
                    error={errorName}
                    onChange={changeName}
                    formControlProps={{
                        fullWidth: true
                    }}
                />
            </GridItem>

        </EditForm>
    )

});