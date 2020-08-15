#ifndef HTTP_CLIENT_H
#define HTTP_CLIENT_H

#include <ESP8266HTTPClient.h>

#include <string>

#include "HttpResponse.h"
using namespace std;

class HttpClient {
   private:
    string _serverURL = "";
    void setUpClient(HTTPClient& client, const string& path);
    void execute(int execMethod, HTTPClient& client, HttpResponse& response);

   public:
    HttpClient(const string& serverURL);
    HttpClient() = default;
    ~HttpClient() = default;

    string getServerURL();
    void setServerURL(const string& serverURL);

    HttpResponse doGet(const string& path);
    HttpResponse doPost(const string& path, const string& body);
    HttpResponse doPatch(const string& path, const string& body);
};

#endif  //HTTP_CLIENT_H