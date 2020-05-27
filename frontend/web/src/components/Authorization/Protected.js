import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";
import React from "react";
import {toJS} from "mobx";

export default function Protected(props) {
    const {role, children, homeID} = props;
    const {homeStore} = useStores();

    React.useEffect(() => {
        if (!homeStore.rolesInHome)
            homeStore.getAllMyRoles();
    }, [homeStore]);

    return useObserver(() => {
        const {rolesInHome} = homeStore;
        let myRole = toJS(rolesInHome);

        return (
            <>
                {rolesInHome && myRole[homeID] === role.role && children}
            </>
        )
    });
}