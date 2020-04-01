import GeneralService from "../GeneralService";
import RoomService from "./RoomService";

export default class DeviceService extends GeneralService {

    static DEVICE_ENDPOINT = "/devices";
    static CAPABILITY_ENDPOINT = "/capability";

    homeID;
    roomID;

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
        return this.fetch(`${DeviceService.DEVICE_ENDPOINT}/${id}`);
    };

    getCapabilities = (id) => {
        return this.fetch(`${DeviceService.DEVICE_ENDPOINT}/${id}${DeviceService.CAPABILITY_ENDPOINT}`)
    };

    createDevice = (device) => {
        return this.post(DeviceService.DEVICE_ENDPOINT, device);
    };

    addDeviceToRoom = (homeID, roomID, deviceID) => {
        return this.post(`${DeviceService.getPath(homeID, roomID)}/${deviceID}`);
    };

    updateDevice = (id, device) => {
        return this.patch(`${DeviceService.DEVICE_ENDPOINT} /${id}`, device);
    };

    deleteDevice = (id) => {
        return this.delete(`${DeviceService.DEVICE_ENDPOINT} /${id}`);
    };
}