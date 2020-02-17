import GeneralService from "./GeneralService";

export default class HomeService extends GeneralService {

    static HOME_ENDPOINT = "/homes";

    constructor(urlServer) {
        super(urlServer);
    }

    getAllHomes = () => {
        return super.fetch(HomeService.HOME_ENDPOINT);
    };

    getHomeByID = (id) => {
        return super.fetch(`${HomeService.HOME_ENDPOINT}/${id}`);
    };

    createHome = (home) => {
        return super.post(HomeService.HOME_ENDPOINT, home);
    };

    updateHome = (id, home) => {
        return super.patch(`${HomeService.HOME_ENDPOINT}/${id}`, home);
    };

    deleteHome = (id) => {
        return super.delete(`${HomeService.HOME_ENDPOINT}/${id}`);
    };

    getDevicesInHome = (id) => {
        return super.fetch(`${HomeService.HOME_ENDPOINT}/${id}/devices`);
    };

    addHomeToUser = (homeID, userID) => {
        return super.post(`/users/${userID}/${HomeService.HOME_ENDPOINT}/${homeID}`, null);
    };


}