import GeneralStore from "../GeneralStore";
import {action, computed, decorate, observable} from "mobx";
import {history} from "../../index";

export default class AuthStore extends GeneralStore {

    _keycloak;

    _authenticated;

    _user;

    _token;

    _refresh_token;

    _authService;

    constructor(authService) {
        super();
        this._authService = authService;
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

    get token() {
        return this._token;
    }

    setToken = (token) => {
        this._token = token;
    };

    get refreshToken() {
        return this._refresh_token;
    }

    setRefreshToken = (refreshToken) => {
        this._refresh_token = refreshToken;
    };

    login = (username, password) => {
        this.startLoading();
        this._authService
            .login(username, password)
            .then(this.setUser)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    get isUserLogged() {
        return this._user !== undefined && this._user !== null;
    };

}

decorate(AuthStore, {
    _user: observable,
    _keycloak: observable,
    _authenticated: observable,
    _token: observable,
    _refresh_token: observable,

    setUser: action,
    setKeycloak: action,
    setAuthenticated: action,
    setToken: action,
    setRefreshToken: action,

    user: computed,
    keycloak: computed,
    token: computed,
    refreshToken: computed,
    isUserLogged: computed,
    isAuthenticated: computed,
});