import GeneralService from "./GeneralService";
import HomeService from "./HomeService";

export default class RoomService extends GeneralService {

    static ROOM_ENDPOINT = "rooms";

    static getPath(homeID) {
        return `${HomeService.HOME_ENDPOINT}/${homeID}/${RoomService.ROOM_ENDPOINT}`;
    }

    constructor(urlServer) {
        super(urlServer);
    }

    getAllRooms = (homeID) => {
        return this.fetch(RoomService.getPath(homeID));
    };

    getRoomByID = (homeID, roomID) => {
        return this.fetch(`${RoomService.getPath(homeID)}/${roomID}`);
    };

    createRoom = (homeID, room) => {
        return this.post(RoomService.getPath(homeID), room);
    };

    addRoomToHome = (homeID, roomID) => {
        return this.post(`${RoomService.getPath(homeID)}/${roomID}`, {});
    };

    updateRoom = (homeID, roomID, room) => {
        return this.patch(`${RoomService.getPath(homeID)}/${roomID}`, room);
    };

    deleteRoom = (homeID, roomID) => {
        return this.delete(`${RoomService.getPath(homeID)}/${roomID}`);
    };
}