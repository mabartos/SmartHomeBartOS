import {action, computed, decorate, observable} from "mobx";
import GeneralStore from "../GeneralStore";

export class HomeStore extends GeneralStore {

    _homes = new Map();

    _devices = new Map();

    _invitations = new Map();

    _rolesInHome = new Map();

    _members = new Map();

    _homeService;

    constructor(homeService) {
        super(homeService);
        this._homeService = this._service;
        this.getAllHomes();
    }

    setUserID = (userID) => {
        this._homeService.setUserID(userID);
    };

    setHomes = (homesList) => {
        this._homes.clear();
        this._homes = this.getMapFromList(homesList);
        this.checkError();
    };

    setHome = (home) => {
        this._homes.set(home.id, home);
        this.checkError();
    };

    setDevices = (deviceList) => {
        this._devices.clear();
        this._devices = this.getMapFromList(deviceList);
        this.checkError();
    };

    setInvitations = (invitationList) => {
        this._invitations = this.getMapFromList(invitationList);
        this.checkError();
    };

    setInvitation = (invitation) => {
        this._invitations.set(invitation.id, invitation);
        this.checkError();
    };

    setRoleInHome = (role) => {
        this._rolesInHome.set(role.homeID, role.role);
        this.checkError();
    };

    setAllRolesInHome = (roleList) => {
        if (roleList) {
            roleList.forEach(role => {
                this._rolesInHome.set(role.homeID, role.role);
            })
        }
    };

    setMembers = (memberList) => {
        if (memberList) {
            memberList.forEach(member => {
                this._members.set(member.user.id, member);
            })
        }
    };

    get homes() {
        return this._homes;
    }

    get devices() {
        return this._devices;
    }

    get invitations() {
        return this._invitations;
    }

    getByIDstore = (id) => {
        return this._homes.get(id);
    };

    get rolesInHome() {
        return this._rolesInHome;
    }

    get members() {
        return this._members;
    }

    reloadHomes = () => {
        const handleError = (error) => {
            this._homes = new Map();
            this.setError(error);
        };
        this.clearActionInvoked();

        this._homeService
            .getAllHomes()
            .then(this.setHomes)
            .catch(handleError);

        this.reloadAllMyRoles();
    };

    stopLoadingAndMessage = (message) => {
        this.stopLoading();
        this.setActionInvoked(message);
    };

    getAllHomes = () => {
        this.clearActionInvoked();
        this.startLoading();

        this.getAllMyRoles();

        this._homeService
            .getAllHomes()
            .then(this.setHomes)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getHomeByID = (id) => {
        this.startLoading();
        this._homeService
            .getHomeByID(id)
            .then(this.setHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    createHome = (home) => {
        this.startLoading();
        this._homeService
            .createHome(home)
            .then(this.setHome)
            .then(this.setActionInvoked("Home is successfully created."))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    updateHome = (id, home) => {
        this.startLoading();
        this._homeService
            .updateHome(id, home)
            .then(this.setHome)
            .then(this.setActionInvoked("Home is successfully updated."))
            .catch(this.setError)
            .finally(this.stopLoading)
    };

    deleteHome = (id) => {
        this.startLoading();
        this._homeService
            .deleteHome(id)
            .then(this.removeFromHomesMap(id))
            .then(this.setActionInvoked("Home is successfully deleted."))
            .catch(this.setError)
            .finally(this.stopLoading)
    };

    getDevicesInHome = (id) => {
        this.startLoading();
        this._homeService
            .getDevicesInHome(id)
            .then(this.setDevices)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    addHomeToUser = (homeID, userID) => {
        this.startLoading();
        this._homeService
            .addHomeToUser(homeID, userID)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    createInvitation = (homeID, invitation) => {
        this.startLoading();
        this._homeService
            .createInvitation(homeID, invitation)
            .then(this.setInvitation)
            .then(this.setActionInvoked(`Invitation was created.`))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    updateInvitation = (homeID, id, invitation) => {
        this.startLoading();
        this._homeService
            .updateInvitation(homeID, id, invitation)
            .then(this.setInvitation)
            .then(this.setActionInvoked(`Invitation '${id}' was updated.`))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    deleteInvitation = (homeID, id) => {
        this.startLoading();
        this._homeService
            .deleteInvitation(homeID, id)
            .then(this.removeFromInvitationMap(id))
            .then(this.setActionInvoked(`Invitation '${id}' was deleted.`))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getMyRoleInHome = (homeID) => {
        this.startLoading();
        this._homeService
            .getMyRoleInHome(homeID)
            .then(this.setRoleInHome)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getAllMyRoles = () => {
        this.startLoading();
        this.reloadAllMyRoles();
        this.stopLoading();
    };

    getMembers = (homeID) => {
        this.startLoading();
        this._homeService
            .getMembers(homeID)
            .then(this.setMembers)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    clearMembers = () => {
        this._members = new Map();
    };

    reloadAllMyRoles = () => {
        this._homeService
            .getAllMyRoles()
            .then(this.setAllRolesInHome)
            .catch(this.setError);
    };

    removeFromHomesMap = (id) => {
        this._homes.delete(id);
    };

    resetStates = () => {
        this._homes.clear();
        this._rolesInHome.clear();
        this._devices.clear();
        this._invitations.clear();
    }
}

decorate(HomeStore, {
    // OBSERVABLE
    _homes: observable,
    _devices: observable,
    _invitations: observable,
    _rolesInHome: observable,
    _members: observable,

    // ACTION
    setHome: action,
    setHomes: action,

    setDevices: action,

    setInvitation: action,
    setInvitations: action,

    setRoleInHome: action,
    setAllRolesInHome: action,

    setMembers: action,
    clearMembers: action,
    resetStates: action,

    // COMPUTED
    homes: computed,
    devices: computed,
    invitations: computed,
    rolesInHome: computed,
    members: computed
});