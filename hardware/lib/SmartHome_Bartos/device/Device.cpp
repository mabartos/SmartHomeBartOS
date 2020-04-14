
#include "Device.h"

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

vector<shared_ptr<Capability>> Device::getCapabilities() {
    return _capabilities;
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
    }
}
