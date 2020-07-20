import React, {forwardRef, useImperativeHandle} from "react";
import {useObserver} from "mobx-react-lite";
import {GeneralModal} from "../../components/Modal/GeneralModal";
import useStores from "../../hooks/useStores";

export const HomeMembersModal = forwardRef((props, ref) => {
    const [open, setOpen] = React.useState(false);
    const [isCleared, setCleared] = React.useState(false);

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
            setCleared(true);
            homeStore.getMembers(uiStore.homeID);
        } else {
            if (!isCleared) {
                homeStore.clearMembers();
                setCleared(true);
            }
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

        return (
            <>
                <GeneralModal open={open} openCallback={handleOpenState} title={"Home Members"}
                              confirmCondition={false}>

                </GeneralModal>
            </>
        )
    });
});