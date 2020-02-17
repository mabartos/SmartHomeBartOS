import {action, computed, observable} from "mobx";
import GeneralStore from "./GeneralStore";

export class UserStore extends GeneralStore {

    @observable
    users = {};

    userService;

    constructor(userService) {
        super();
        this.userService = userService;
    }

    @action
    setUsers = (usersList) => {
        this.users = usersList.reduce((map, user) => {
            map[user.id] = user;
            return map;
        });
    };

    @action
    setUser = (user) => {
        this.users[user.id] = user;
    };

    @computed
    get users() {
        return this.users;
    }

    @computed
    get usersValues() {
        return Object.values(this.users);
    }

    getAllUsers = () => {
        this.startLoading();
        this.userService
            .getAllUsers()
            .then(this.setUsers)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getUserByID = (id) => {
        this.startLoading();
        this.userService
            .getUserByID(id)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getUserByEmail = (email) => {
        this.startLoading();
        this.userService
            .getUserByEmail(email)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getUserByUsername = (username) => {
        this.startLoading();
        this.userService
            .getUserByUsername(username)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    createUser = (user) => {
        this.startLoading();
        this.userService
            .createUser(user)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    updateUser = (id, user) => {
        this.startLoading();
        this.userService
            .updateUser(id, user)
            .then(this.setUser)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    deleteUser = (id) => {
        this.startLoading();
        this.userService
            .deleteUser(id)
            .catch(super.setError)
            .finally(super.stopLoading);
    };
}

