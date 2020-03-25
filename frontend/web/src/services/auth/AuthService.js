import React from "react";
import GeneralService from "../GeneralService";

export default class AuthService extends GeneralService {

    static AUTH_ENDPOINT = "/auth";

    constructor(urlServer) {
        super(urlServer);
    }
};