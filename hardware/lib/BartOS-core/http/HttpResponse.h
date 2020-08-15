#ifndef HTTP_RESPONSE_H
#define HTTP_RESPONSE_H

using namespace std;
#include <string>

class HttpResponse {
   private:
    int _code;
    string _payload;

   public:
    HttpResponse() = default;
    HttpResponse(const int& code, const string& payload);
    ~HttpResponse() = default;

    int getResponseCode();
    void setResponseCode(const int code);

    string& getPayload();

    void setPayload(const string& payload);
    void setPayload(const char* payload);
};

#endif  //HTTP_RESPONSE_H