import GeneralStore from "../GeneralStore";
import {action, computed, decorate, observable} from "mobx";

export class DeviceStore extends GeneralStore {

    _devices = new Map();

    _capabilities = new Map();

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
        this.addCapabilities(devicesList);
        this.checkError();
        return devicesList;
    };

    setDevice = (device) => {
        this._devices.set(device.id, device);
        this.addCapabilitiesForDevice(device.id);
        this.checkError();
    };

    setDeviceByID = (id, device) => {
        this._devices.set(id, device);
        this.addCapabilitiesForDevice(id);
        this.checkError();
    };

    setCapabilities = (capsList) => {
        this._capabilities.clear();
        this._capabilities = this.getMapFromList(capsList);
        this.checkError();
    };

    addAllCapabilities=(capList)=>{
        capList.forEach(cap=>{
            this.setCapability(cap);
        });
    };

    addCapabilitiesForDevice=(deviceID)=>{
        this._deviceService
            .getCapabilities(deviceID)
            .then(this.addAllCapabilities)
            .catch(this.setError)
    };

    addCapabilities = (deviceList) => {
        deviceList.forEach(device => {
            this.addCapabilitiesForDevice(device.id);
        });
        this.checkError();
    };

    setCapability = (cap) => {
        this._capabilities.set(cap.id, cap);
        this.checkError();
    };

    get devices() {
        return this._devices;
    }

    getDevice = (id) => {
        return this._devices.get(id);
    };

    get capabilities(){
        return this._capabilities;
    }

    getCapability=(id)=>{
        return this._capabilities.get(id);
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
            .then(this.removeFromDeviceMap(deviceID))
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
        this._capabilities.forEach((val,key)=>{
            if(val.deviceID===id){
                this._capabilities.delete(val.id);
            }
        });
    };

    deleteCapabilities=()=>{
        this._capabilities.clear();
    }
}

decorate(DeviceStore, {
    _devices: observable,
    _capabilities: observable,

    setDevices: action,
    setDevice: action,

    setCapabilities: action,
    setCapability: action,
    addCapabilities: action,

    devices: computed,
    capabilities: computed
});