import GeneralStore from "../GeneralStore";
import {action, computed, decorate, observable} from "mobx";

export class DeviceStore extends GeneralStore {

    _devices = new Map();

    _deviceService;

    constructor(deviceService) {
        super(deviceService);
        this._deviceService = this._service;
        this.getAllDevices();
    }

    setHomeID = (id) => {
        this._deviceService.setHomeID(id);
    };

    setRoomID = (id) => {
        this._deviceService.setRoomID(id);
    };

    setDevices = (devicesList) => {
        this._devices.clear();
        this._devices = this.getMapFromList(devicesList);
        this.checkError();
    };

    setDevice = (device) => {
        this._devices.set(device.id, device);
        this.checkError();
    };

    setDeviceByID = (id, device) => {
        this._devices.set(id, device);
        this.checkError();
    };

    setCapabilities = (deviceID, caps) => {
        let device = this.getDevice(deviceID);
        device.caps = caps;
        this.setDeviceByID(deviceID, device);
    };

    get devices() {
        return this._devices;
    }

    getDevice = (id) => {
        return this._devices.get(id);
    };

    reloadDevices = () => {
        const handleError = (error) => {
            this._devices.clear();
            this.setError(error);
        };
        this.clearActionInvoked();

        this._deviceService
            .getAllDevices()
            .then(this.setDevices)
            .catch(handleError)
    };

    getAllDevices = () => {
        this.startLoading();
        this._deviceService
            .getAllDevices()
            .then(this.setDevices)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getDeviceByID = (id) => {
        this.startLoading();
        this._deviceService
            .getDeviceByID(id)
            .then(this.setDevice)
            .catch(this.setError)
            .finally(this.stopLoading);

    };

    getCapabilities = (id) => {
        this.startLoading();
        this._deviceService
            .getCapabilities(id)
            .then(caps => this.setCapabilities(id, caps))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    createDevice = (device) => {
        this.startLoading();
        this._deviceService
            .createDevice(device)
            .then(this.setDevice)
            .then(this.setActionInvoked("Device is successfully created."))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    addDeviceToRoom = (homeID, roomID, deviceID) => {
        this.startLoading();
        this._deviceService
            .addDeviceToRoom(homeID, roomID, deviceID)
            .then(this.setActionInvoked("Device is successfully added to room."))
            .then(this.setDevice)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    removeDeviceFromRoom = (deviceID) => {
        this.startLoading();
        this._deviceService
            .removeDeviceFromRoom(deviceID)
            .then(this.setActionInvoked("Device is removed from room"))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    updateDevice = (id, device) => {
        this.startLoading();
        this._deviceService
            .updateDevice(id, device)
            .then(this.setDevice)
            .then(this.setActionInvoked("Device is successfully updated."))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    deleteDevice = (id) => {
        this.startLoading();
        this._deviceService
            .deleteDevice(id)
            .then(this.removeFromDeviceMap(id))
            .then(this.setActionInvoked("Device is successfully deleted."))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    removeFromDeviceMap = (id) => {
        this._devices.delete(id);
    };
}

decorate(DeviceStore, {
    _devices: observable,

    setDevices: action,
    setDevice: action,

    devices: computed
});