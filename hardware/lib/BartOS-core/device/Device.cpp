
#include "Device.h"

#include <string>

#include "capability/utils/CapabilityUtils.h"
#include "http/HttpClient.h"
#include "mqtt/MessageForwarder.h"
#include "mqtt/MqttClient.h"
#include "wifiUtils/WifiUtils.h"

extern MqttClient client;
extern WifiUtils wifiUtils;
extern HttpClient httpClient;

Device::Device() {
    setName("Dev_" + NumberGenerator::generateIntToString(2000, 9999));
}

string Device::getName() {
    return _name;
}

void Device::setName(const string &name) {
    _name = name;
}

long Device::getID() {
    return _ID;
}
void Device::setID(const long &id) {
    _ID = id;
}

long Device::getHomeID() {
    return _homeID;
}
void Device::setHomeID(const long &homeID) {
    _homeID = homeID;
}

long Device::getRoomID() {
    return _roomID;
}

void Device::setRoomID(const long &roomID) {
    _roomID = roomID;
}

bool Device::isInitialized() {
    return _initialized;
}
void Device::setInitialized(bool initialized) {
    _initialized = initialized;
}

long Device::getManageMsgID() {
    return _manageMsgID;
}

void Device::setManageMsgID(const long &msgID) {
    _manageMsgID = msgID;
}

/* TOPIC */

string Device::getHomeTopic() {
    string topic = "homes/";
    return (_homeID != -1) ? (topic + NumberGenerator::longToString(_homeID)) : "";
}

string Device::getHomeTopicWildCard() {
    return (getHomeTopic() != "") ? getHomeTopic() + "/#" : "";
}

string Device::getRoomTopic() {
    if (_roomID != -1 && getHomeTopic() != "") {
        string roomID = NumberGenerator::longToString(_roomID);
        return (getHomeTopic() + "/rooms/" + roomID);
    }
    return "";
}

string Device::getRoomTopicWildCard() {
    return (getRoomTopic() != "") ? getRoomTopic() + "/#" : "";
}

string Device::getDeviceTopic() {
    return (_ID != -1) ? (getHomeTopic() + "/devices/" + NumberGenerator::longToString(_ID)) : "";
}

// Manage topics
string Device::getCreatePath() {
    string homeTopic = getHomeTopic();
    return (homeTopic != "") ? string(homeTopic + "/create") : "";
}

string Device::getConnectPath() {
    string deviceTopic = getDeviceTopic();
    return (deviceTopic != "") ? string(deviceTopic + "/connect") : "";
}

string Device::getLogoutTopic() {
    string homeTopic = getHomeTopic();
    return (homeTopic != "") ? string(homeTopic + "/logout/" + NumberGenerator::longToString(getID())) : "";
}

string Device::getEraseAllTopic() {
    string homeTopic = getHomeTopic();
    return (homeTopic != "") ? string(homeTopic + "/erase-all/" + NumberGenerator::longToString(getID())) : "";
}

string Device::getEraseTopicWild() {
    string homeTopic = getHomeTopic();
    return (homeTopic != "") ? string(homeTopic + "/erase-all/#") : "";
}

string Device::getGetRoomTopic() {
    string homeTopic = getHomeTopic();
    return (homeTopic != "") ? string(homeTopic + "/get-room/" + NumberGenerator::longToString(getID())) : "";
}

/* CAPS */
vector<shared_ptr<Capability>> Device::getCapabilities() {
    return _capabilities;
}

void Device::setCapabilities(vector<shared_ptr<Capability>> &caps) {
    _capabilities = caps;
}

auto Device::getCapByPinAndType(const uint8_t &pin, const CapabilityType &type) -> shared_ptr<Capability> {
    for (auto &item : getCapabilities()) {
        if (item->getPin() == pin && item->getType() == type) {
            return item;
        }
    }
    return nullptr;
}

void Device::addCapability(shared_ptr<Capability> cap) {
    _capabilities.push_back(cap);
}

void Device::removeCapability(long id) {
    vector<shared_ptr<Capability>> caps = getCapabilities();
    for (unsigned i = 0; i < caps.size(); i++) {
        if (caps[i]->getID() == id) {
            _capabilities.erase(caps.begin() + i);
        }
    }
}

void Device::initAllCapabilities() {
    for (auto &item : getCapabilities()) {
        item->init();
    }
}

void Device::executeAllCapabilities() {
    for (auto &item : getCapabilities()) {
        item->execute();
        client.checkAvailability();
    }
}

// CREATE

DynamicJsonDocument Device::getCreateJSON() {
    const size_t capacity = getCreateJSONSize();
    DynamicJsonDocument create(capacity);

    create["name"] = _name.c_str();
    JsonArray caps = create.createNestedArray("capabilities");

    for (auto &item : getCapabilities()) {
        JsonObject obj = caps.createNestedObject();
        item->editCreateCapNested(obj);
    }

    serializeJson(create, Serial);
    return create;
}

size_t Device::getCreateJSONSize() {
    return JSON_ARRAY_SIZE(getCapabilities().size()) + getCapabilities().size() * (JSON_OBJECT_SIZE(3) + 100) + JSON_OBJECT_SIZE(4) + 500;
}

void Device::publishCreateMessage() {
    char buffer[2048];

    serializeJson(getCreateJSON(), buffer);

    HttpResponse resp = httpClient.doPost(getCreatePath(), string(buffer));

    DynamicJsonDocument doc(1024);
    DeserializationError err = deserializeJson(doc, resp.getPayload().c_str(), resp.getPayload().size());
    if (err) {
        Serial.println(err.c_str());
        return;
    }

    JsonObject obj = doc.as<JsonObject>();

    vector<string> keys{"id", "name", "capabilities"};
    if (!MessageForwarder::containKeys(obj, keys) || resp.getResponseCode() != 200 || resp.getResponseCode() != 201) {
        return;
    }

    long ID = obj["id"];
    setID(ID);
    MessageForwarder::manageCreateSPIFS(obj, ID);

    client.getMQTT().subscribe(getDeviceTopic().c_str());
    setInitialized(true);
    client.reconnect();
    doc.garbageCollect();
}

// CONNECT

DynamicJsonDocument Device::getConnectJSON() {
    const size_t capacity = getConnectJSONSize();
    DynamicJsonDocument connect(capacity);
    long msgID = NumberGenerator::generateLong(100, 999);
    setManageMsgID(msgID);

    connect["msgID"] = msgID;
    connect["id"] = getID();
    connect["name"] = _name.c_str();

    return connect;
}

size_t Device::getConnectJSONSize() {
    return JSON_OBJECT_SIZE(3) + 80;
}

void Device::publishConnectMessage() {
    char buffer[2048];

    serializeJson(getConnectJSON(), buffer);

    HttpResponse resp = httpClient.doGet(getConnectPath());

    DynamicJsonDocument doc(1024);
    DeserializationError err = deserializeJson(doc, resp.getPayload().c_str(), resp.getPayload().size());
    if (err) {
        Serial.println(err.c_str());
        return;
    }

    JsonObject obj = doc.as<JsonObject>();

    vector<string> keys{"id", "name", "capabilities"};
    if (!MessageForwarder::containKeys(obj, keys) || resp.getResponseCode() != 200) {
        return;
    }

    const char *name = obj["name"];
    if (strcmp(name, getName().c_str()) != 0) {
        setName(string(name));
    }

    if (obj.containsKey("roomID")) {
        long roomID = obj["roomID"];
        if (roomID != getRoomID()) {
            setRoomID(roomID);
        }
    }

    if (getRoomTopicWildCard() != "") {
        client.getMQTT().subscribe(getRoomTopicWildCard().c_str());
    }

    setCapsIDFromJSON(obj);
    setInitialized(true);
    client.getMQTT().subscribe(getDeviceTopic().c_str());

    client.reconnect();
    doc.garbageCollect();
}

void Device::setCapsIDFromJSON(const JsonObject &obj) {
    setCapsIDFromJSON(obj, false);
}

void Device::setCapsIDFromJSON(const JsonObject &obj, bool shouldCreate) {
    if (obj.containsKey("capabilities")) {
        StaticJsonDocument<1024> doc;

        JsonArray caps = obj["capabilities"];

        for (JsonObject cap : caps) {
            long capID = cap["id"];
            uint8_t pin = cap["pin"];
            const char *type = cap["type"];

            auto p_cap = getCapByPinAndType(pin, CapabilityUtils::getFromString(string(type)));
            if (p_cap != nullptr) {
                p_cap->setID(capID);
            }
        }
        doc.garbageCollect();
    }
}

void Device::publishGetRoom() {
    char buffer[300];

    StaticJsonDocument<512> doc;
    doc["resp"] = false;
    const long deviceID = getID();
    doc["deviceID"] = deviceID;

    serializeJson(doc, buffer);

    const char *result = getGetRoomTopic().c_str();
    client.getMQTT().publish_P(result, buffer, false);
}

void Device::eraseAll() {
    Serial.println("Reset device");
    client.getMQTT().disconnect();
    wifiUtils.reset();
    delay(2000);
    ESP.restart();
    delay(3000);
}

string Device::getBrokerURL() {
    return _brokerURL;
}
void Device::setBrokerURL(const string &brokerURL) {
    _brokerURL = brokerURL;
}

string Device::getServerURL() {
    return _serverURL;
}

void Device::setServerURL(const string &serverURL) {
    _serverURL = serverURL;
}
