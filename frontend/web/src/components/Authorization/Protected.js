import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";
import React from "react";

export default function Protected(props) {
    const {role, children, homeID} = props;
    const {homeStore} = useStores();

    React.useEffect(() => {
        if (!homeStore.rolesInHome)
            homeStore.getAllMyRoles();
    }, []);

    return useObserver(() => {
        const {rolesInHome} = homeStore;
        
        return (
            <>
                {rolesInHome && rolesInHome.get(homeID) === role.role && children}
            </>
        )
    });
}