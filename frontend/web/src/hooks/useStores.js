import React from "react"
import {storesContext} from "../index"

const useStores = () => React.useContext(storesContext)

export default useStores;