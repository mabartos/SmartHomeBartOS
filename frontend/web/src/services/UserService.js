import GeneralService from "./GeneralService";

export default class UserService extends GeneralService {

    static USER_ENDPOINT = "/users";

    constructor(urlServer) {
        super(urlServer);
    }

    getAllUsers = () => {
        return this.fetch(UserService.USER_ENDPOINT);
    };

    getUserByID = (id) => {
        return this.fetch(`${UserService.USER_ENDPOINT}/${id}`);
    };

    getUserByEmail = (email) => {
        return this.fetch(`${UserService.USER_ENDPOINT}/search?email=${email}`);
    };

    getUserByUsername = (username) => {
        return this.fetch(`${UserService.USER_ENDPOINT}/search?username=${username}`);
    };

    createUser = (user) => {
        return this.post(UserService.USER_ENDPOINT, user);
    };

    updateUser = (id, user) => {
        return this.patch(`${UserService.USER_ENDPOINT}/${id}`, user);
    };

    deleteUser = (id) => {
        return this.delete(`${UserService.USER_ENDPOINT}/${id}`);
    };


}