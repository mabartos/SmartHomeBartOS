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
import {Redirect, Route, Router, Switch} from "react-router-dom";
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

export const history = createBrowserHistory();

export const backendURL = "http://localhost:8888";
export const keycloakURL = "http://localhost:8180/auth/realms/SmartHome";

//seconds
export const TOKEN_LIFESPAN = 60;

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
    roomStore
};

export const HomeComponent = {
    HOME: "home",
    USER: "user",
    ROOM: "room",
    DEVICE: "device"
};

export const storesContext = React.createContext(stores);
export const servicesContext = React.createContext(services);

ReactDOM.render(
    <Router history={history}>
        <Switch>
            <Route path="/admin" component={Admin}/>
            <Route path="/auth/login" component={LoginPage}/>
            <Redirect from="/" to="/admin"/>
        </Switch>
    </Router>,
    document.getElementById("root")
);
