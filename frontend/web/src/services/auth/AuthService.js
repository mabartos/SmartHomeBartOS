import React from "react";
import GeneralService from "../GeneralService";

export default class AuthService extends GeneralService {

    static AUTH_ENDPOINT = "/auth";

    _token;

    _refreshToken;

    setToken = (token) => {
        this._token = token;
    };

    get token() {
        return this._token;
    }

    setRefreshToken = (refreshToken) => {
        this._refreshToken = refreshToken;
    };

    get refreshToken() {
        return this._refreshToken;
    }

    constructor(urlServer) {
        super(urlServer);
    }

    getUserInfo = () => {
        return this.fetchExternal("/protocol/openid-connect/userinfo");
    };
};