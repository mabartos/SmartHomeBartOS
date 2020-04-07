import GeneralService from "../GeneralService";

export default class UserService extends GeneralService {

    static USER_ENDPOINT = "/users";
    static INVITATION_ENDPOINT = "/invitations";
    static ACCEPT_INVITATION_ENDPOINT = "/accept";
    static DISMISS_INVITATION_ENDPOINT = "/dismiss";

    constructor(urlServer) {
        super(urlServer);
    }

    getUserByID = (id) => {
        return this.fetch(`${UserService.USER_ENDPOINT}/${id}`);
    };

    getUserByEmail = (email) => {
        return this.fetch(`${UserService.USER_ENDPOINT}/searchEmail/${email}`);
    };

    getUserByUsername = (username) => {
        return this.fetch(`${UserService.USER_ENDPOINT}/searchUsername/${username}`);
    };

    getAllInvitations = () => {
        return this.fetch(`${UserService.INVITATION_ENDPOINT}`);
    };

    getInvitationByID = (id) => {
        return this.fetch(`${UserService.INVITATION_ENDPOINT}/${id}`)
    };

    createInvitation = (invitation) => {
        return this.post(`${UserService.INVITATION_ENDPOINT}`, invitation);
    };

    updateInvitation = (id, invitation) => {
        return this.patch(`${UserService.INVITATION_ENDPOINT}/${id}`, invitation);
    };

    deleteInvitation = (id) => {
        return this.delete(`${UserService.INVITATION_ENDPOINT}/${id}`);
    };

    acceptInvitation = (id) => {
        return this.fetch(`${UserService.INVITATION_ENDPOINT}/${id}${UserService.ACCEPT_INVITATION_ENDPOINT}`);
    };

    dismissInvitation = (id) => {
        return this.fetch(`${UserService.INVITATION_ENDPOINT}/${id}${UserService.DISMISS_INVITATION_ENDPOINT}`);
    };

}