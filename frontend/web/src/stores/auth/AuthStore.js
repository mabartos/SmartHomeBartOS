import GeneralStore from "../GeneralStore";
import {action, computed, decorate, observable} from "mobx";
import {history} from "../../index";
import * as Keycloak from "keycloak-js";
import KeycloakConfig from "../../keycloak";

export default class AuthStore extends GeneralStore {

    _keycloak;

    _authenticated;

    _user;

    _authService;

    constructor(authService) {
        super(authService);
        this._authService = this._service;
    }

    setUser = (user) => {
        this._user = user;
        this.checkError();
        /*history.push("/admin");*/
    };

    get user() {
        return this._user;
    }

    setKeycloak = (keycloak) => {
        this._keycloak = keycloak;
    };

    get keycloak() {
        return this._keycloak;
    };

    setAuthenticated = (authenticated) => {
        this._authenticated = authenticated;
    };

    get isAuthenticated() {
        return this._authenticated;
    };

    getToken() {
        return this._authService.token;
    }

    setToken = (token) => {
        this._authService.setToken(token);
    };

    getRefreshToken() {
        return this._authService.refreshToken;
    }

    setRefreshToken = (refreshToken) => {
        this._authService.setRefreshToken(refreshToken);
    };

    initKeycloak = () => {
        if (!this.isAuthenticated) {
            this.reconnectKeycloak();
        }
    };

    reconnectKeycloak = () => {
        let keycloak = Keycloak(KeycloakConfig);
        this._keycloak = keycloak;

        const auth = keycloak.init({onLoad: 'login-required'}).then(authenticated => {
            localStorage.setItem("keycloak-token", keycloak.token);
            localStorage.setItem("keycloak-refresh-token", keycloak.refreshToken);
            this.getUserInfo().then(this.setUser).catch();

            return authenticated;
        }).catch(err => console.error(err));

        setInterval(() => {
            keycloak.updateToken(30).then((refresh) => {
                if (refresh) {
                    localStorage.setItem("keycloak-token", keycloak.token);
                    localStorage.setItem("keycloak-refresh-token", keycloak.refreshToken);
                    this.getUserInfo().then(this.setUser);
                }
                refresh ? console.info('Token refreshed' + refresh) : console.warn('Token not refreshed, valid for ' +
                    Math.round(keycloak.tokenParsed.exp + keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
            }).catch((err) => {
                console.error('Failed to refresh token');
            });
        }, 5000);

        auth.then(authenticated => {
            this._authenticated = authenticated;
        });

        this.setToken(localStorage.getItem("keycloak-token"));
        this.setRefreshToken(localStorage.getItem("keycloak-refresh-token"));
    };

    logout = () => {
        if (this._keycloak) {
            this._keycloak.logout();
            //history.push("/");
        }
    };

    getUserInfo = () => {
        return this._authService.getUserInfo();
    };

}

decorate(AuthStore, {
    _user: observable,
    _keycloak: observable,
    _authenticated: observable,

    setUser: action,
    setKeycloak: action,
    setAuthenticated: action,

    user: computed,
    keycloak: computed,
    isAuthenticated: computed,
});