import React from "react";
import {useObserver} from "mobx-react-lite";
import {SemipolarLoading} from 'react-loadingg';
import useStores from "../../hooks/useStores";
import {useParams} from "react-router-dom";

export default function Room(props) {
    const {authStore} = useStores();
    const {roomID} = useParams();

    return useObserver(() => {
        const {isAuthenticated} = authStore;

        if (isAuthenticated) {
            return (
                <div>
                    Room ID={roomID}
                </div>
            )
        } else {
            return (<SemipolarLoading/>)
        }

    });
}