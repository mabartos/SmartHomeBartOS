import GeneralService from "../GeneralService";
import RoomService from "./RoomService";

export default class DeviceService extends GeneralService {

    static DEVICE_ENDPOINT = "/devices";
    static CAPABILITY_ENDPOINT = "/caps";

    homeID;
    roomID;

    static getTopic(homeID, roomID) {
        return `homes/${homeID}/${RoomService.ROOM_ENDPOINT}/${roomID}`;
    }

    static getFullTopic(homeID, roomID, capability, id) {
        return DeviceService.getTopic(homeID, roomID) + "/" + capability + "/" + id;
    }

    constructor(urlServer) {
        super(urlServer);
    }

    setHomeID = (id) => {
        this.homeID = id;
    };

    setRoomID = (id) => {
        this.roomID = id;
    };

    getPath() {
        return `${RoomService.getPath(this.homeID)}/${this.roomID}${DeviceService.DEVICE_ENDPOINT}`;
    }

    getAllDevices = () => {
        return this.fetch(this.getPath());
    };

    getDeviceByID = (id) => {
        return this.fetch(`${this.getPath()}/${id}`);
    };

    getCapabilitiesForDevice = (id) => {
        return this.fetch(`${this.getPath()}/${id}${DeviceService.CAPABILITY_ENDPOINT}`)
    };

    createDevice = (device) => {
        return this.post(`${this.getPath()}`, device);
    };

    addDeviceToRoom = (homeID, roomID, deviceID) => {
        this.setHomeID(homeID);
        this.setRoomID(roomID);
        return this.post(`${this.getPath()}/${deviceID}/add`);
    };

    removeDeviceFromRoom = (deviceID) => {
        return this.post(`${this.getPath()}/${deviceID}/remove`);
    };

    updateDevice = (id, device) => {
        return this.patch(`${this.getPath()}/${id}`, device);
    };

    updateCapability = (deviceID, capID, capability) => {
        return this.patch(`${this.getPath()}/${deviceID}${DeviceService.CAPABILITY_ENDPOINT}/${capID}`, capability);
    };

    deleteDevice = (id) => {
        return this.delete(`${this.getPath()}/${id}`);
    };
}