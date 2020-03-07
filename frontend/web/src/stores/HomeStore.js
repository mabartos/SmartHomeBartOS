import {action, computed, decorate, observable} from "mobx";
import GeneralStore from "./GeneralStore";

export class HomeStore extends GeneralStore {

    _homes = {};

    _devicesInHome = {};

    _homeService;

    constructor(homeService) {
        super();
        this._homeService = homeService;
    }

    setHomes = (homesList) => {
        this._homes = homesList.reduce((map, home) => {
            map[home.id] = home;
            return map;
        });
    };

    setHome = (home) => {
        this._homes[home.id] = home;
    };

    setDevicesInHome = (devices) => {
        this._homes = devices.reduce((map, home) => {
            map[home.id] = home;
            return map;
        });
    };

    setDevice = (device) => {
        this._homes[device.id] = device;
    };

    /*get homes() {
        return this._homes;
    }

    set homes(homes){
        this._homes=homes;
    }

    get homesValues() {
        return Object.values(this._homes);
    }

    get devices() {
        return this._devicesInHome;
    }

    get devicesValues() {
        return Object.values(this._devicesInHome);
    }*/

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
    _devicesInHome: observable,

    setHome: action,
    setHomes: action,
    setDevicesInHome: action,
    setDevice: action,

    /*homes: computed,
    homesValues: computed,
    devices: computed,
    devicesValue: computed*/
});