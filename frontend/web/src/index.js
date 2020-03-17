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
import UserService from "./services/UserService";
import HomeService from "./services/HomeService";
import RoomService from "./services/RoomService";
import DeviceService from "./services/DeviceService";
import {HomeStore} from "./stores/HomeStore";
import {UserStore} from "./stores/UserStore";
import {RoomStore} from "./stores/RoomStore";

export const history = createBrowserHistory();

const urlServer = "http://localhost:8888";

// SERVICES
const userService = new UserService(urlServer);
const homeService = new HomeService(urlServer);
const roomService = new RoomService(urlServer);
const deviceService = new DeviceService(urlServer);

// STORES
const homeStore = new HomeStore(homeService);
const userStore = new UserStore(userService);
const roomStore = new RoomStore(roomService);
/*
const homeStore = undefined;
*/

const services = {
    userService,
    homeService,
    roomService,
    deviceService
};

const stores = {
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
            <Redirect from="/" to="/admin/dashboard"/>
        </Switch>
    </Router>,
    document.getElementById("root")
);
