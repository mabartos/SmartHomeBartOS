import {action, computed, decorate, observable} from "mobx";
import GeneralStore from "../GeneralStore";

export class UserStore extends GeneralStore {

    _users = [];

    _userService;

    constructor(userService) {
        super(userService);
        this._userService = this._service;
    }

    setUsers = (usersList) => {
        for (let i = 0; i < usersList.length; i++) {
            this._users.push(usersList[i]);
        }
    };

    setUser = (user) => {
        this._users[user.id] = user;
    };

    get users() {
        return this._users;
    }

    get usersValues() {
        return Object.values(this._users);
    }

    getAllUsers = () => {
        this.startLoading();
        this._userService
            .getAllUsers()
            .then(this.setUsers)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getUserByID = (id) => {
        this.startLoading();
        this._userService
            .getUserByID(id)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getUserByEmail = (email) => {
        this.startLoading();
        this._userService
            .getUserByEmail(email)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getUserByUsername = (username) => {
        this.startLoading();
        this._userService
            .getUserByUsername(username)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    createUser = (user) => {
        this.startLoading();
        this._userService
            .createUser(user)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    updateUser = (id, user) => {
        this.startLoading();
        this._userService
            .updateUser(id, user)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    deleteUser = (id) => {
        this.startLoading();
        this._userService
            .deleteUser(id)
            .catch(super.setError)
            .finally(super.stopLoading);
    };
}

decorate(UserStore, {
    _users: observable,

    setUsers: action,
    setUser: action,

    users: computed
});

