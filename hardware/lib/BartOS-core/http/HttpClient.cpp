#include "HttpClient.h"

HttpClient::HttpClient(const string& serverURL) : _serverURL(serverURL) {
}

void HttpClient::setUpClient(HTTPClient& client, const string& path) {
    string resultPath = _serverURL + "/" + path;

    client.begin(resultPath.c_str());

    client.addHeader("Content-Type", "application/json");
}

string HttpClient::getServerURL() {
    return _serverURL;
}

void HttpClient::setServerURL(const string& serverURL) {
    _serverURL = serverURL;
}

void HttpClient::execute(int execMethodCode, HTTPClient& client, HttpResponse& response) {
    response.setResponseCode(execMethodCode);
    if (execMethodCode > 0) {
        response.setPayload(client.getString().c_str());
    }

    client.end();
}

HttpResponse HttpClient::doGet(const string& path) {
    HTTPClient http;
    HttpResponse resp;

    setUpClient(http, path);
    execute(http.GET(), http, resp);

    return resp;
}

HttpResponse HttpClient::doPost(const string& path, const string& body) {
    HTTPClient http;
    HttpResponse resp;

    setUpClient(http, path);
    execute(http.POST(body.c_str()), http, resp);

    return resp;
}

HttpResponse HttpClient::doPatch(const string& path, const string& body) {
    HTTPClient http;
    HttpResponse resp;

    setUpClient(http, path);
    execute(http.PATCH(body.c_str()), http, resp);

    return resp;
}