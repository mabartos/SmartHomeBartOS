import GeneralStore from "../GeneralStore";
import {action, computed, decorate, observable} from "mobx";

export class RoomStore extends GeneralStore {

    _rooms = new Map();

    _roomService;

    constructor(roomService) {
        super(roomService);
        this._roomService = this._service;
    }

    setRooms = (roomsList) => {
        this._rooms = this.getMapFromList(roomsList);
        this.checkError();
    };

    setRoom = (room) => {
        this._rooms.set(room.id, room);
        this.checkError();
    };

    get rooms() {
        return this._rooms;
    }

    reloadAllRooms = (homeID) => {
        const handleError = (error) => {
            this._rooms = new Map();
            this.setError(error);
        };

        this.clearActionInvoked();

        this._roomService
            .getAllRooms(homeID)
            .then(this.setRooms)
            .catch(handleError);
    };

    getAllRooms = (homeID) => {
        this.startLoading();
        this._roomService
            .getAllRooms(homeID)
            .then(this.setRooms)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getRoomByID = (homeID, roomID) => {
        this.startLoading();
        this._roomService
            .getRoomByID(homeID, roomID)
            .then(this.setRoom)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    createRoom = (homeID, room) => {
        this.startLoading();
        this._roomService
            .createRoom(homeID, room)
            .then(this.setRoom)
            .then(this.setActionInvoked("Room was successfully created"))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    addRoomToHome = (homeID, roomID) => {
        this.startLoading();
        this._roomService
            .addRoomToHome(homeID, roomID)
            .then(this.setRooms)
            .then(this.setActionInvoked("Room was successfully added to home"))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    updateRoom = (homeID, roomID, room) => {
        this.startLoading();
        this._roomService
            .updateRoom(homeID, roomID, room)
            .then(this.setRoom)
            .then(this.setActionInvoked("Room was successfully updated"))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    deleteRoom = (homeID, roomID) => {
        this.startLoading();
        this._roomService
            .deleteRoom(homeID, roomID)
            .then(this.setActionInvoked("Room was successfully deleted"))
            .then(this.removeFromRoomMap(roomID))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    removeFromRoomMap = (id) => {
        this._rooms.delete(id);
    };
}

decorate(RoomStore, {
    _rooms: observable,

    setRoom: action,
    setRooms: action,

    rooms: computed
})
;