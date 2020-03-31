export default class GeneralService {
    _urlServer;

    constructor(urlServer) {
        this._urlServer = urlServer
    }

    fetch = (path, settings) => {
        return new Promise((resolve, reject) => {
            const headers = {
                "Accept": "application/json",
                "Content-type": "application/json"
            };
            const token = localStorage.getItem("keycloak-token");
            if (token) {
                headers["Authorization"] = `Bearer ${token}`;
            }

            fetch(`${this._urlServer}${path}`, {...settings, headers})
                .then(response => {
                    switch (response.status) {
                        case 200:
                        case 201:
                            response.json()
                                .then(resolve)
                                .catch(reject);
                            break;
                        case 400:
                            reject(response.reason);
                            break;
                        case 401:
                            reject(response.reason);
                            break;
                        case 403:
                            reject(response.reason);
                            break;
                        case 404:
                            reject(response.reason);
                            break;
                        case 409:
                            reject(response.reason);
                            break;
                        case 500:
                            reject(response.reason);
                            break;
                        default:
                            reject("Error occurred");
                            break;
                    }
                })
                .catch(reject);
        });
    };

    post = (path, body) => {
        return this.fetch(path, {
            body: JSON.stringify(body),
            method: "POST"
        });
    };

    patch = (path, body) => {
        return this.fetch(path, {
            body: JSON.stringify(body),
            method: "PATCH"
        });
    };

    delete = (path) => {
        return this.fetch(path, {
            method: "DELETE"
        });
    };
}