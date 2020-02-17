import GeneralService from "./GeneralService";

export default class UserService extends GeneralService {

    static USER_ENDPOINT = "/users";

    constructor(urlServer) {
        super(urlServer);
    }

    getAllUsers = () => {
        return super.fetch(UserService.USER_ENDPOINT);
    };

    getUserByID = (id) => {
        return super.fetch(`${UserService.USER_ENDPOINT}/${id}`);
    };

    getUserByEmail = (email) => {
        return super.fetch(`${UserService.USER_ENDPOINT}/search?email=${email}`);
    };

    getUserByUsername = (username) => {
        return super.fetch(`${UserService.USER_ENDPOINT}/search?username=${username}`);
    };

    createUser = (user) => {
        return super.post(UserService.USER_ENDPOINT, user);
    };

    updateUser = (id, user) => {
        return super.patch(`${UserService.USER_ENDPOINT}/${id}`, user);
    };

    deleteUser = (id) => {
        return super.delete(`${UserService.USER_ENDPOINT}/${id}`);
    };


}