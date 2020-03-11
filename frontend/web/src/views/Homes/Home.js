import React from "react";
import {useHistory, useParams, useRouteMatch} from "react-router-dom";
import {useObserver} from "mobx-react-lite";
import useStores from "../../hooks/useStores";
import GridContainer from "../../components/Grid/GridContainer";
import HomeCard from "../../components/BartCard/HomeCard";


export default function Home(props) {
    const {homeStore, roomStore} = useStores();
    const {homeID} = useParams();
    const {path} = useRouteMatch();
    const history = useHistory();
    const id = parseInt(homeID || -1);

    React.useEffect(() => {
        if (!homeStore.homes[id]) {
            homeStore.getHomeByID(id);
            roomStore.getAllRooms(id);
        }
    }, [homeStore, roomStore, id]);

    return useObserver(() => {
        const {error, loading, homes} = homeStore;
        const {rooms} = roomStore;
        
        const allRooms = [...rooms].map(([key, item], index) => (
            <HomeCard key={index} id={item.id} title={item.name} color="info"/>
        ));

        return (
            <div>
                <GridContainer>
                    {allRooms}
                </GridContainer>
            </div>
        );
    });

}