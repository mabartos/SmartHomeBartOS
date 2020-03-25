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
        history.push("/admin");
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
        return this._service.token;
    }

    setToken = (token) => {
        this._service.setToken(token);
    };

    getRefreshToken() {
        return this._authService.refreshToken;
    }

    setRefreshToken = (refreshToken) => {
        this._service.setRefreshToken(refreshToken);
    };

    initKeycloak = () => {
        if (!this.isAuthenticated) {
            let keycloak = Keycloak(KeycloakConfig);
            this._keycloak = keycloak;

            const auth = keycloak.init({onLoad: 'login-required'}).then(authenticated => {
                localStorage.setItem("keycloak-token", keycloak.token);
                localStorage.setItem("keycloak-refresh-token", keycloak.refreshToken);

                setTimeout(() => {
                    keycloak.updateToken(70).then((refresh) => {
                        refresh ? console.debug('Token refreshed' + refresh) : console.warn('Token not refreshed, valid for ' +
                            Math.round(keycloak.tokenParsed.exp + keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
                    }).catch(() => {
                        console.error('Failed to refresh token');
                    });

                }, 60000);
                return authenticated;
            }).catch(err => console.error(err))

            auth.then(authenticated => {
                this._authenticated = authenticated;
            });

            this.setToken(localStorage.getItem("keycloak-token"));
            this.setRefreshToken(localStorage.getItem("keycloak-refresh-token"));

            this.getInfo().then(this.setUser).catch();
        }
    };

    getInfo = () => {
        return new Promise((resolve, reject) => {
            fetch(`${this.keycloak.authServerUrl}realms/${this.keycloak.realm}/protocol/openid-connect/userinfo`, {
                headers: new Headers({
                    'Authorization': 'Bearer ' + this.getToken(),
                    "Accept": "application/json",
                    "Content-type": "application/json"
                })
            }).then(response => {
                response.json().then(resolve).catch(reject);
            });
        });
    };

    logout = () => {
        if (this._keycloak) {
            this._keycloak.logout();
            history.push("/");
        }
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