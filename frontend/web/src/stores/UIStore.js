import {action, computed, decorate, observable} from "mobx";
import dashboardRoutes from "../routes";

export default class UIStore {
    _homeID;
    _roomID;
    _actualPage;
    _myHomeRole;

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

    get myHomeRole() {
        return this._myHomeRole;
    }

    setMyHomeRole = (role) => {
        this._myHomeRole = role;
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
    _myHomeRole: observable,

    setHomeID: action,
    setRoomID: action,
    setActualPage: action,
    setMyHomeRole: action,

    isHomePageActive: computed,
    isRoomPageActive: computed,

    homeID: computed,
    roomID: computed,
    actualPage: computed,
    myHomeRole: computed
});


