import * as React from "react";
import {servicesContext} from "../index";

const useServices = () => React.useContext(servicesContext);

export default useServices;