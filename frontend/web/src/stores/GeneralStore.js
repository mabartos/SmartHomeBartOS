import {action, computed, decorate, observable} from "mobx";

export default class GeneralStore {
    _loading = false;

    _error = null;

    _actionInvoked = false;

    startLoading = () => {
        this._loading = true;
    };

    stopLoading = () => {
        this._loading = false;
    };

    setError = (message) => {
        if (message !== this._error) {
            this._error = message;
        }
    };

    setActionInvoked = (message) => {
        this._actionInvoked = message;
    };

    clearActionInvoked = () => {
        this._actionInvoked = undefined;
    };

    get loading() {
        return this._loading;
    }

    get error() {
        return this._error;
    }

    get actionInvoked() {
        return this._actionInvoked;
    };

    checkError = () => {
        if (this._error !== undefined) {
            this._error = undefined;
        }
    };

    getMapFromList = (list) => {
        let map = new Map();
        for (let i = 0; i < list.length; i++) {
            map.set(list[i].id, list[i]);
        }
        return map;
    };
}
decorate(GeneralStore, {
    _loading: observable,
    _error: observable,
    _actionInvoked: observable,

    startLoading: action,
    stopLoading: action,
    setError: action,
    setActionInvoked: action,

    loading: computed,
    error: computed,
    actionInvoked: computed
});