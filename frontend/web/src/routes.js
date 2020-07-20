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
// @material-ui/icons
import Dashboard from "@material-ui/icons/Dashboard";
import Person from "@material-ui/icons/Person";
import HomeIcon from '@material-ui/icons/Home';
// core components/views for Admin layout
import DashboardPage from "views/Dashboard/Dashboard.js";
import UserProfile from "views/UserProfile/UserProfile.js";
// core components/views for RTL layout
import Homes from "./views/Homes/Homes";
import Home from "./views/Homes/Home.jsx";
import Room from "./views/Room/Room";

export const RoutePages = {
    ALL_HOMES: 'allHomes',
    HOME: 'home',
    ROOM: 'room',
    USER_PROFILE: 'userProfile',
    DASHBOARD: 'dashboard',
    OTHER: 'other'
};

const dashboardRoutes = [
    {
        page: RoutePages.DASHBOARD,
        path: "/home",
        name: "Home",
        icon: Dashboard,
        component: DashboardPage,
        layout: "/admin"
    },
    {
        page: RoutePages.ROOM,
        path: "/my-homes/:homeID/rooms/:roomID",
        name: "Devices",
        icon: Person,
        component: Room,
        layout: "/admin",
        inSidebar: false
    },
    {
        page: RoutePages.HOME,
        path: "/my-homes/:homeID",
        name: "Rooms",
        icon: Person,
        component: Home,
        layout: "/admin",
        inSidebar: false
    },
    {
        page: RoutePages.ALL_HOMES,
        path: "/my-homes",
        name: "My Homes",
        icon: HomeIcon,
        component: Homes,
        layout: "/admin"
    },
    {
        page: RoutePages.USER_PROFILE,
        path: "/user",
        name: "User Profile",
        icon: Person,
        component: UserProfile,
        layout: "/admin"
    }
];

export default dashboardRoutes;
