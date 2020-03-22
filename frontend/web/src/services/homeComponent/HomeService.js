import GeneralService from "../GeneralService";
import React from "react";

export default class HomeService extends GeneralService {

    static HOME_ENDPOINT = "/homes";

    constructor(urlServer) {
        super(urlServer);
    }

    getAllHomes = () => {
        return this.fetch(HomeService.HOME_ENDPOINT);
    };

    getHomeByID = (id) => {
        return this.fetch(`${HomeService.HOME_ENDPOINT}/${id}`);
    };

    createHome = (home) => {
        return this.post(HomeService.HOME_ENDPOINT, home);
    };

    updateHome = (id, home) => {
        return this.patch(`${HomeService.HOME_ENDPOINT}/${id}`, home);
    };

    deleteHome = (id) => {
        return this.delete(`${HomeService.HOME_ENDPOINT}/${id}`);
    };

    getDevicesInHome = (id) => {
        return this.fetch(`${HomeService.HOME_ENDPOINT}/${id}/devices`);
    };

    addHomeToUser = (homeID, userID) => {
        return this.post(`/users/${userID}/${HomeService.HOME_ENDPOINT}/${homeID}`, null);
    };


}