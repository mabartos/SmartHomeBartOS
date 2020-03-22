import React from "react";
import GeneralService from "../GeneralService";

export default class AuthService extends GeneralService {

    static AUTH_ENDPOINT = "/auth";

    constructor(urlServer) {
        super(urlServer);
    }

    login = (username, password) => {
        let authData = {};
        authData.username = username;
        authData.password = password;
        return this.post(`${AuthService.AUTH_ENDPOINT}/login`, authData);
    };

};