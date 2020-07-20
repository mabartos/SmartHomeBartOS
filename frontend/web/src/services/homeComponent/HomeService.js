import GeneralService from "../GeneralService";
import React from "react";

export default class HomeService extends GeneralService {

    static HOME_ENDPOINT = "/homes";
    static INVITATION_ENDPOINT = "/invitations";

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

    createInvitation = (homeID, invitation) => {
        return this.post(`${this.getURL(homeID)}${HomeService.INVITATION_ENDPOINT}`, invitation);
    };

    updateInvitation = (homeID, id, invitation) => {
        return this.patch(`${this.getURL(homeID)}${HomeService.INVITATION_ENDPOINT}/${id}`, invitation);
    };

    deleteInvitation = (homeID, id) => {
        return this.delete(`${this.getURL(homeID)}${HomeService.INVITATION_ENDPOINT}/${id}`);
    };

    getMyRoleInHome = (homeID) => {
        return this.fetch(`${this.getURL(homeID)}/my-role`);
    };

    getAllMyRoles = () => {
        return this.fetch(`${this.getURL()}/my-roles`);
    };

    getMembers = (homeID) => {
        return this.fetch(`${this.getURL(homeID)}/members`);
    };
}