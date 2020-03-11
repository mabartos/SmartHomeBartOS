import {action, computed, decorate, observable} from "mobx";
import GeneralStore from "./GeneralStore";

export class HomeStore extends GeneralStore {

    _homes = new Map();

    _homeService;

    constructor(homeService) {
        super();
        this._homeService = homeService;
        this.getAllHomes();
    }

    setHomes = (homesList) => {
        for (let i = 0; i < homesList.length; i++) {
            this._homes.set(homesList[i].id, homesList[i]);
        }
    };

    setHome = (home) => {
        this._homes.set(home.id, home);
    };

    get homes() {
        return this._homes;
    }

    getByIDstore = (id) => {
        return this._homes.get(id);
    };

    getAllHomes = () => {
        this.startLoading();
        this._homeService
            .getAllHomes()
            .then(this.setHomes)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getHomeByID = (id) => {
        this.startLoading();
        this._homeService
            .getHomeByID(id)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    createHome = (home) => {
        this.startLoading();
        this._homeService
            .createHome(home)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    updateHome = (id, home) => {
        this.startLoading();
        this._homeService
            .updateHome(id, home)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    deleteHome = (id) => {
        this.startLoading();
        this._homeService
            .deleteHome(id)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getDevicesInHome = (id) => {
        this.startLoading();
        this._homeService
            .getDevicesInHome(id)
            .then(this.setDevicesInHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    addHomeToUser = (homeID, userID) => {
        this.startLoading();
        this._homeService
            .addHomeToUser(homeID, userID)
            .catch(this.setError)
            .finally(this.stopLoading);
    };
}

decorate(HomeStore, {
    _homes: observable,

    setHome: action,
    setHomes: action,

    homes: computed
});