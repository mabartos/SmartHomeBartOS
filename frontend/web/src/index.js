/*!

=========================================================
* Material Dashboard React - v1.8.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/material-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from "react";
import ReactDOM from "react-dom";
import {createBrowserHistory} from "history";
import {BrowserRouter, Redirect, Route, Router, Switch} from "react-router-dom";
// core components
import Admin from "layouts/Admin.js";

import "assets/css/material-dashboard-react.css?v=1.8.0";
import UserService from "./services/homeComponent/UserService";
import HomeService from "./services/homeComponent/HomeService";
import RoomService from "./services/homeComponent/RoomService";
import DeviceService from "./services/homeComponent/DeviceService";
import {HomeStore} from "./stores/homeComponent/HomeStore";
import {UserStore} from "./stores/homeComponent/UserStore";
import {RoomStore} from "./stores/homeComponent/RoomStore";
import LoginPage from "./views/SignIn/LoginPage";
import AuthService from "./services/auth/AuthService";
import AuthStore from "./stores/auth/AuthStore";
import {DeviceStore} from "./stores/homeComponent/DeviceStore";

export const backendURL = "http://127.0.0.1:8888";
export const keycloakURL = "http://127.0.0.1:8180/auth/realms/SmartHome";

export const BROKER_URL_REGEX = "(tcp|http|https)://(.+):.*";

// SERVICES
const authService = new AuthService(keycloakURL);

const userService = new UserService(backendURL);
const homeService = new HomeService(backendURL);
const roomService = new RoomService(backendURL);
const deviceService = new DeviceService(backendURL);

// STORES
// Auth store must be first initialized
const authStore = new AuthStore(authService);

const homeStore = new HomeStore(homeService);
const userStore = new UserStore(userService);
const roomStore = new RoomStore(roomService);
const deviceStore = new DeviceStore(deviceService);

const services = {
    userService,
    homeService,
    roomService,
    deviceService,
    authService
};

const stores = {
    authStore,
    homeStore,
    userStore,
    roomStore,
    deviceStore
};

export const HomeComponent = {
    HOME: "home",
    USER: "user",
    ROOM: "room",
    DEVICE: "device",
    CAPABILITY: "capability"
};

export const storesContext = React.createContext(stores);
export const servicesContext = React.createContext(services);

ReactDOM.render(
    <BrowserRouter  history={createBrowserHistory()}>
        <Switch>
            <Route path="/admin" component={Admin}/>
            <Route path="/index" component={LoginPage}/>
            <Redirect from="/" to="/index"/>
        </Switch>
    </BrowserRouter>,
    document.getElementById("root")
);
