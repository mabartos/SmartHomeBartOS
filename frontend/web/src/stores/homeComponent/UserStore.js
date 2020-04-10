import {action, computed, decorate, observable} from "mobx";
import GeneralStore from "../GeneralStore";

export class UserStore extends GeneralStore {

    _users = new Map();

    _invitations = new Map();

    _userService;

    _authService;

    constructor(userService, authService) {
        super(userService);
        this._userService = this._service;
        this._authService = authService;
    }

    setUsers = (usersList) => {
        this._users = this.getMapFromList(usersList);
    };

    setUser = (user) => {
        this._users.set(user.id, user);
    };

    setInvitations = (invitationList) => {
        this._invitations = this.getMapFromList(invitationList);
    };

    setInvitation = (invitation) => {
        this._invitations.set(invitation.id, invitation);
    };

    get users() {
        return this._users;
    }

    get invitations() {
        return this._invitations;
    }

    get usersValues() {
        return Object.values(this._users);
    }

    getUserByID = (id) => {
        this.startLoading();
        this._userService
            .getUserByID(id)
            .then(this.setUser)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getUserByEmail = (email) => {
        this.startLoading();
        this._userService
            .getUserByEmail(email)
            .then(this.setUser)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getUserByUsername = (username) => {
        this.startLoading();
        this._userService
            .getUserByUsername(username)
            .then(this.setUser)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getAllInvitations = () => {
        this.startLoading();
        this._userService
            .getAllInvitations()
            .then(this.setInvitations)
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    getInvitationByID = (id) => {
        this.startLoading();
        this._userService
            .getInvitationByID(id)
            .then(this.setInvitation)
            .catch(this.setError)
            .finally(this.stopLoading);
    };
    
    acceptInvitation = (id) => {
        this.startLoading();
        this._userService
            .acceptInvitation(id)
            .then(this.setActionInvoked(`Invitation '${id}' was accepted.`))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    dismissInvitation = (id) => {
        this.startLoading();
        this._userService
            .dismissInvitation(id)
            .then(this.setActionInvoked(`Invitation '${id}' was dismissed.`))
            .catch(this.setError)
            .finally(this.stopLoading);
    };

    reloadInvitations = () => {
        const handleError = (error) => {
            this._invitations.clear();
            this.setError(error);
        };
        this.clearActionInvoked();

        this._userService
            .getAllInvitations()
            .then(this.setInvitations)
            .catch(handleError)
    };

    removeUsers = () => {
        this._users.clear();
    };

    removeFromInvitationMap = (id) => {
        this._invitations.delete(id);
    };
}

decorate(UserStore, {
    _users: observable,
    _invitations: observable,

    setUsers: action,
    setUser: action,

    setInvitations: action,
    setInvitation: action,

    users: computed,
    invitations: computed
});

