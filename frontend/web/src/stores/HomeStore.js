import {action, computed, observable} from "mobx";

export class HomeStore {

    @observable
    homes = {};

    @observable
    devicesInHome = {};

    @observable
    loading = false;

    @observable
    error = null;

    @observable
    fetched = false;

    homeService;

    constructor(homeService) {
        this.homeService = homeService;
    }

    @action
    startLoading = () => {
        this.loading = true;
    };

    @action
    stopLoading = () => {
        this.loading = false;
    };

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

    @action
    setError = (message) => {
        this.error = message;
    };

    @action
    setFetched = () => {
        this.fetched = true;
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
    get loading() {
        return this.loading;
    }

    @computed
    get error() {
        return this.error;
    }

    @computed
    get fetched() {
        return this.fetched;
    }

    @computed
    get devices() {
        return this.devices;
    }

    @computed
    get devicesValues() {
        return Object.values(this.devices);
    }

    getAllHomes = () => {
        this.startLoading();
        this.homeService
            .getAllHomes()
            .then(this.setHomes)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getHomeByID = (id) => {
        this.startLoading();
        this.homeService
            .getHomeByID(id)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    createHome = (home) => {
        this.startLoading();
        this.homeService
            .createHome(home)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    updateHome = (id, home) => {
        this.startLoading();
        this.homeService
            .updateHome(id, home)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    deleteHome = (id) => {
        this.startLoading();
        this.homeService
            .deleteHome(id)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getDevicesInHome = (id) => {
        this.startLoading();
        this.homeService
            .getDevicesInHome(id)
            .then(this.setDevicesInHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    addHomeToUser = (homeID, userID) => {
        this.startLoading();
        this.homeService
            .addHomeToUser(homeID, userID)
            .catch(this.setError)
            .finally(this.stopLoading);
    };
}