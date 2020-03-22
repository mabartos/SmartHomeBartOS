import GeneralStore from "../GeneralStore";
import {action, computed, decorate, observable} from "mobx";

export default class AuthStore extends GeneralStore {

    _user;

    _authService;

    constructor(authService) {
        super();
        this._authService = authService;
    }

    setUser = (user) => {
        this._user = user;
        this.checkError();
    };

    get user() {
        return this._user;
    }

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

    setUser: action,

    user: computed,
    isUserLogged: computed
});