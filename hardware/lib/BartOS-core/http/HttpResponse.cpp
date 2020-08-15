#include "HttpResponse.h"

HttpResponse::HttpResponse(const int& code, const string& payload) : _code(code), _payload(payload) {
}

int HttpResponse::getResponseCode() {
    return _code;
}

void HttpResponse::setResponseCode(const int code) {
    _code = code;
}

string& HttpResponse::getPayload() {
    return _payload;
}

void HttpResponse::setPayload(const string& payload) {
    _payload = payload;
}

void HttpResponse::setPayload(const char* payload) {
    _payload = string(payload);
}