import GeneralStore from "../GeneralStore";
import {action, computed, decorate, observable} from "mobx";
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
        this._authenticated=false;
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
        if (!this._authenticated) {
            this.reconnectKeycloak();
        }
    };

    reconnectKeycloak = () => {
        let keycloak = Keycloak(KeycloakConfig);
        this._keycloak = keycloak;

        const auth = keycloak.init({onLoad: 'login-required',checkLoginIframe:false}).then(authenticated => {
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
            }).catch((err) => {
                console.error('Failed to refresh token');
            });
        }, 5000);

        auth.then(authenticated => {
            this._authenticated = authenticated;
        }).catch();

        this.setToken(localStorage.getItem("keycloak-token"));
        this.setRefreshToken(localStorage.getItem("keycloak-refresh-token"));
    };

    logout = () => {
        if (this._keycloak) {
            localStorage.removeItem("keycloak-token");
            localStorage.removeItem("keycloak-refresh-token");
            this.startLoading();
            this._keycloak.logout({redirectUri: window.location.origin});
            this.stopLoading();
        }
    };

    edit = () => {
        if (this._keycloak) {
            this.startLoading();
            this._keycloak.accountManagement();
            this.stopLoading();
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