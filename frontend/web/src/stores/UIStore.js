import {action, computed, decorate, observable} from "mobx";
import dashboardRoutes from "../routes";

export default class UIStore {
    _homeID;
    _roomID;
    _actualPage;

    get homeID() {
        return this._homeID;
    };

    setHomeID = (homeID) => {
        this._homeID = homeID;
    };

    get roomID() {
        return this._roomID;
    };

    setRoomID = (roomID) => {
        this._roomID = roomID;
    };

    get isHomePageActive() {
        return this._homeID !== undefined && this._roomID === undefined;
    };

    get isRoomPageActive() {
        return this._homeID !== undefined && this._roomID !== undefined;
    };

    get actualPage() {
        return this._actualPage;
    }

    setActualPage = (actualPage) => {
        this._roomID = undefined;
        this._homeID = undefined;
        if ([...dashboardRoutes].filter(route => actualPage === route.page).length > 0) {
            this._actualPage = actualPage;
        }
    };
}

decorate(UIStore, {
    _homeID: observable,
    _roomID: observable,
    _actualPage: observable,

    setHomeID: action,
    setRoomID: action,
    setActualPage: action,

    isHomePageActive: computed,
    isRoomPageActive: computed,

    homeID: computed,
    roomID: computed,
    actualPage: computed
});


