export default class GeneralService {
    _urlServer;

    static getObjectFromString = (string) => {
        try {
            return JSON.parse(string);
        } catch (e) {
            return null;
        }
    };

    static getStringFromObject = (object) => {
        try {
            return JSON.stringify(object);
        } catch (e) {
            console.error(e);
            return null;
        }
    };

    constructor(urlServer) {
        this._urlServer = urlServer
    }

    fetchExternal = (path, settings) => {
        return this.fetch(path, settings, true);
    };

    fetch = (path, settings, external) => {
        return new Promise((resolve, reject) => {
            const headers = {
                "Accept": "application/json",
                "Content-type": "application/json"
            };
            const token = localStorage.getItem("keycloak-token");
            if (token) {
                headers["Authorization"] = `Bearer ${token}`;
            }

            const resultPath = external ? `${this._urlServer}${path}` : `${this._urlServer}/api${path}`;

            fetch(resultPath, {...settings, headers})
                .then(response => {
                    switch (response.status) {
                        case 200:
                        case 201:
                            response.json()
                                .then(resolve)
                                .catch(reject);
                            break;
                        default:
                            reject(response.reason);
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