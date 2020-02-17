import GeneralService from "./GeneralService";
import HomeService from "./HomeService";

export default class RoomService extends GeneralService {

    static ROOM_ENDPOINT = "/rooms";

    static getPath(homeID) {
        return `${HomeService.HOME_ENDPOINT}/${homeID}/${RoomService.ROOM_ENDPOINT}`;
    }

    constructor(urlServer) {
        super(urlServer);
    }

    getAllRooms = (homeID) => {
        return super.fetch(RoomService.getPath(homeID));
    };

    getRoomByID = (homeID, roomID) => {
        return super.fetch(`${RoomService.getPath(homeID)}/${roomID}`);
    };

    createRoom = (homeID, room) => {
        return super.post(RoomService.getPath(homeID), room);
    };

    addRoomToHome = (homeID, roomID) => {
        return super.post(`${RoomService.getPath(homeID)}/${roomID}`, {});
    };

    updateRoom = (homeID, roomID, room) => {
        return super.patch(`${RoomService.getPath(homeID)}/${roomID}`, room);
    };

    deleteRoom = (homeID, roomID) => {
        return super.delete(`${RoomService.getPath(homeID)}/${roomID}`);
    };
}