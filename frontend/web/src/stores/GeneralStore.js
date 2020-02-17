import {action, computed, observable} from "mobx";

export default class GeneralStore {
    @observable
    loading = false;

    @observable
    error = null;

    @observable
    fetched = false;

    @action
    startLoading = () => {
        this.loading = true;
    };

    @action
    stopLoading = () => {
        this.loading = false;
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
    };
}