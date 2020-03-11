import {action, computed, decorate, observable} from "mobx";
import GeneralStore from "./GeneralStore";

export class HomeStore extends GeneralStore {

    _homes = [];

    _homeService;

    constructor(homeService) {
        super();
        this._homeService = homeService;
    }

    setHomes = (homesList) => {
        for (let i = 0; i < homesList.length; i++) {
            this._homes.push(homesList[i]);
        }
    };

    setHome = (home) => {
        this._homes[home.id] = home;
    };

    get homes() {
        return this._homes;
    }

    get homesValues() {
        return Object.values(this._homes);
    }

    getAllHomes = () => {
        this.startLoading();
        this._homeService
            .getAllHomes()
            .then(this.setHomes)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getHomeByID = (id) => {
        this.startLoading();
        this._homeService
            .getHomeByID(id)
            .then(this.setHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    createHome = (home) => {
        this.startLoading();
        this._homeService
            .createHome(home)
            .then(this.setHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    updateHome = (id, home) => {
        this.startLoading();
        this._homeService
            .updateHome(id, home)
            .then(this.setHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    deleteHome = (id) => {
        this.startLoading();
        this._homeService
            .deleteHome(id)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getDevicesInHome = (id) => {
        this.startLoading();
        this._homeService
            .getDevicesInHome(id)
            .then(this.setDevicesInHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    addHomeToUser = (homeID, userID) => {
        this.startLoading();
        this._homeService
            .addHomeToUser(homeID, userID)
            .catch(super.setError)
            .finally(super.stopLoading);
    };
}

decorate(HomeStore, {
    _homes: observable,

    setHome: action,
    setHomes: action,

    homes: computed,
    homesValues: computed,
});