export default class GeneralService {
    urlServer;

    constructor(urlServer) {
        this.urlServer = urlServer
    }

    fetch = (path, settings) => {
        return new Promise((resolve, reject) => {

            const headers = {
                /* "Accept": "application/json",
                 "Content-type": "application/json"*/
            };

            fetch(`${this.urlServer}${path}`, {...settings, headers})
                .then(response => {
                    console.log(`${this.urlServer}${path}`);
                    console.log(response);

                    switch (response.status) {
                        case 200:
                        case 201:
                            response.json()
                                .then(resolve)
                                .catch(reject);
                            break;
                        case 400:
                            reject("Bad request");
                            break;
                        case 401:
                            reject("You are not authorized");
                            break;
                        case 403:
                            reject("Forbidden");
                            break;
                        case 404:
                            reject("Not found");
                            break;
                        case 409:
                            reject("Conflict");
                            break;
                        case 500:
                            reject("Server error");
                            break;
                        default:
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