import {action, decorate, observable,computed} from "mobx";

export default class GeneralStore {
    _loading = false;

    _error = null;

    _fetched = false;

    startLoading = () => {
        this._loading = true;
    };

    stopLoading = () => {
        this._loading = false;
    };

    setError = (message) => {
        this._error = message;
    };

    setFetched = () => {
        this._fetched = true;
    };

    /*get _loading() {
        return this._loading;
    }

    get error() {
        return this._error;
    }

    get fetched() {
        return this._fetched;
    };*/
}
decorate(GeneralStore, {
    _loading: observable,
    _error: observable,
    _fetched: observable,

    startLoading: action,
    stopLoading: action,
    setError: action,
    setFetched:action,
/*
    loading:computed,
    error:computed,
    fetched:computed*/
});