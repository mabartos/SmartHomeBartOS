import React from "react";
import GeneralService from "../GeneralService";
import {keycloakURL} from "../../index";

export default class AuthService extends GeneralService {

    static AUTH_ENDPOINT = "/auth";

    constructor(urlServer) {
        super(urlServer);
    }

    getUserInfo = () => {
        return this.fetch("/protocol/openid-connect/userinfo");
    };
};