import GeneralService from "../GeneralService";
import React from "react";

export default class HomeService extends GeneralService {

    static HOME_ENDPOINT = "/homes";

    _userID;

    constructor(urlServer) {
        super(urlServer);
    }

    setUserID = (userID) => {
        this._userID = userID;
    };

    getURL(homeID) {
        const basic = `${HomeService.HOME_ENDPOINT}`;
        return (homeID !== undefined) ? basic + `/${homeID}` : basic;
    }

    getAllHomes = () => {
        return this.fetch(this.getURL());
    };

    getHomeByID = (homeID) => {
        return this.fetch(this.getURL(homeID));
    };

    createHome = (home) => {
        return this.post(this.getURL(), home);
    };

    updateHome = (homeID, home) => {
        return this.patch(this.getURL(homeID), home);
    };

    deleteHome = (homeID) => {
        return this.delete(this.getURL(homeID));
    };

    getDevicesInHome = (homeID) => {
        return this.fetch(this.getURL(homeID) + "/devices");
    };

    addHomeToUser = (homeID) => {
        return this.post(this.getURL(homeID), null);
    };
}