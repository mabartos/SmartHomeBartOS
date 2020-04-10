import React from "react";
import {Redirect, Route, Switch} from "react-router-dom";
// creates a beautiful scrollbar
import PerfectScrollbar from "perfect-scrollbar";
import "perfect-scrollbar/css/perfect-scrollbar.css";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
// core components
import Navbar from "components/Navbars/Navbar.js";
import Footer from "components/Footer/Footer.js";
import Sidebar from "components/Sidebar/Sidebar.js";

import routes from "routes.js";

import styles from "assets/jss/material-dashboard-react/layouts/adminStyle.js";

import bgImage from "assets/img/home.jpg";
import logo from "assets/img/reactlogo.png";
import useStores from "../hooks/useStores";
import {useObserver} from "mobx-react-lite";
import {SemipolarLoading} from "react-loadingg";

let ps;

const sidebarRoutes = () => {
    let result = [];
    routes.map((item) => {
        if (item.inSidebar === undefined || item.inSidebar !== false) {
            result.push(item);
        }
    });
    return result;
};

const switchRoutes = (
    <Switch>
        {routes.map((prop, key) => {
            if (prop.layout === "/admin") {
                return (
                    <Route
                        path={prop.layout + prop.path}
                        component={prop.component}
                        key={key}
                    />
                );
            }
            return null;
        })}
        <Redirect from="/admin" to="/admin/dashboard"/>
    </Switch>
);

const useStyles = makeStyles(styles);

export default function Admin({...rest}) {

    const {authStore, homeStore} = useStores();

    // styles
    const classes = useStyles();
    // ref to help us initialize PerfectScrollbar on windows devices
    const mainPanel = React.createRef();
    // states and functions
    const [image, setImage] = React.useState(bgImage);
    const [color, setColor] = React.useState("blue");
    const [fixedClasses, setFixedClasses] = React.useState("dropdown show");
    const [mobileOpen, setMobileOpen] = React.useState(false);
    const handleImageClick = image => {
        setImage(image);
    };
    const handleColorClick = color => {
        setColor(color);
    };
    const handleFixedClick = () => {
        if (fixedClasses === "dropdown") {
            setFixedClasses("dropdown show");
        } else {
            setFixedClasses("dropdown");
        }
    };
    const handleDrawerToggle = () => {
        setMobileOpen(!mobileOpen);
    };
    const getRoute = () => {
        return window.location.pathname !== "/admin";
    };

    const resizeFunction = () => {
        if (window.innerWidth >= 1400) {
            setMobileOpen(false);
        }
    };

    React.useEffect(() => {
        authStore.initKeycloak();
        homeStore.getAllHomes();

        const intervalHomes = setInterval(() => {
            homeStore.reloadHomes();
        }, 2000);

        return () => {
            clearInterval(intervalHomes);
        };

    }, []);

    // initialize and destroy the PerfectScrollbar plugin
    React.useEffect(() => {
        if (navigator.platform.indexOf("Win") > -1) {
            ps = new PerfectScrollbar(mainPanel.current, {
                suppressScrollX: true,
                suppressScrollY: false
            });
            document.body.style.overflow = "hidden";
        }
        window.addEventListener("resize", resizeFunction);
        // Specify how to clean up after this effect:
        return function cleanup() {
            if (navigator.platform.indexOf("Win") > -1) {
                ps.destroy();
            }
            window.removeEventListener("resize", resizeFunction);
        };
    }, [mainPanel]);

    return useObserver(() => {
        const {isAuthenticated, keycloak, token, user} = authStore;

        if (isAuthenticated) {
            return (
                <div className={classes.wrapper}>
                    <Sidebar
                        routes={sidebarRoutes()}
                        logoText={"Smart Home"}
                        logo={logo}
                        image={image}
                        handleDrawerToggle={handleDrawerToggle}
                        open={mobileOpen}
                        color={color}
                        {...rest}
                    />
                    <div className={classes.mainPanel} ref={mainPanel}>
                        <Navbar
                            routes={routes}
                            handleDrawerToggle={handleDrawerToggle}
                            {...rest}
                        />

                        {/* On the /maps route we want the map to be on full screen - this is not possible if the content and conatiner classes are present because they have some paddings which would make the map smaller */}
                        {getRoute() ? (
                            <div className={classes.content}>
                                <div className={classes.container}>{switchRoutes}</div>
                            </div>
                        ) : (
                            <div className={classes.map}>{switchRoutes}</div>
                        )}
                        {getRoute() ? <Footer/> : null}
                    </div>
                </div>

            );
        } else {
            return (
                <div>
                    <SemipolarLoading/>
                </div>)
        }
    });
};
