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
        this._homes.clear();
        this._homes = this.getMapFromList(homesList);
        this.checkError();
    };

    setHome = (home) => {
        this._homes.set(home.id, home);
        this.checkError();
    };

    get homes() {
        return this._homes;
    }

    getByIDstore = (id) => {
        return this._homes.get(id);
    };

    reloadHomes = () => {
        const handleError = (error) => {
            this._homes = new Map();
            this.setError(error);
        };

        this._homeService
            .getAllHomes()
            .then(this.setHomes)
            .catch(handleError)
    };

    getAllHomes = () => {
        this.clearActionInvoked();
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
        this.setActionInvoked("Home is successfully created.")
    };

    updateHome = (id, home) => {
        this.startLoading();
        this._homeService
            .updateHome(id, home)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
        this.setActionInvoked("Home is successfully updated.")
    };

    deleteHome = (id) => {
        this.startLoading();
        this._homeService
            .deleteHome(id)
            .catch(this.setError)
            .finally(this.stopLoading);
        this.setActionInvoked("Home is successfully deleted.")
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