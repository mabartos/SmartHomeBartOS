import {action, computed, observable} from "mobx";
import GeneralStore from "./GeneralStore";

export class HomeStore extends GeneralStore {

    @observable
    homes = {};

    @observable
    devicesInHome = {};

    homeService;

    constructor(homeService) {
        super();
        this.homeService = homeService;
    }

    @action
    setHomes = (homesList) => {
        this.homes = homesList.reduce((map, home) => {
            map[home.id] = home;
            return map;
        });
    };

    @action
    setHome = (home) => {
        this.homes[home.id] = home;
    };

    @action
    setDevicesInHome = (devices) => {
        this.homes = devices.reduce((map, home) => {
            map[home.id] = home;
            return map;
        });
    };

    @action
    setDevice = (device) => {
        this.homes[device.id] = device;
    };

    @computed
    get homes() {
        return this.homes;
    }

    @computed
    get homesValues() {
        return Object.values(this.homes);
    }

    @computed
    get devices() {
        return this.devicesInHome;
    }

    @computed
    get devicesValues() {
        return Object.values(this.devicesInHome);
    }

    getAllHomes = () => {
        this.startLoading();
        this.homeService
            .getAllHomes()
            .then(this.setHomes)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getHomeByID = (id) => {
        this.startLoading();
        this.homeService
            .getHomeByID(id)
            .then(this.setHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    createHome = (home) => {
        this.startLoading();
        this.homeService
            .createHome(home)
            .then(this.setHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    updateHome = (id, home) => {
        this.startLoading();
        this.homeService
            .updateHome(id, home)
            .then(this.setHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    deleteHome = (id) => {
        this.startLoading();
        this.homeService
            .deleteHome(id)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    getDevicesInHome = (id) => {
        this.startLoading();
        this.homeService
            .getDevicesInHome(id)
            .then(this.setDevicesInHome)
            .catch(super.setError)
            .finally(super.stopLoading);
    };

    addHomeToUser = (homeID, userID) => {
        this.startLoading();
        this.homeService
            .addHomeToUser(homeID, userID)
            .catch(super.setError)
            .finally(super.stopLoading);
    };
}