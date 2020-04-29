import {dangerColor, grayColor, hexToRgb, successColor, whiteColor} from "assets/jss/material-dashboard-react.js";
import {blackColor} from "../../material-dashboard-react";
//import {dangerColor} from "../../material-dashboard-react";

const generalTileStyles = {
    container:{
        minHeight: 300,
        minWidth: "200px"
    },
    successText: {
        color: successColor[0]
    },
    upArrowCardCategory: {
        width: "16px",
        height: "16px"
    },
    cardStatusInActive:{
      color:dangerColor[0],
        margin: "0",
        fontSize: "14px",
        marginTop: "0",
        paddingTop: "10px",
        marginBottom: "0"
    },
    cardStatusActive: {
        color: successColor[0],
        margin: "0",
        fontSize: "14px",
        marginTop: "0",
        paddingTop: "10px",
        marginBottom: "0"
    },
    cardStatusNormal: {
        color: grayColor[1],
        margin: "0",
        fontSize: "14px",
        marginTop: "0",
        paddingTop: "10px",
        marginBottom: "0"
    },
    cardCategory: {
        color: grayColor[0],
        margin: "0",
        fontSize: "14px",
        marginTop: "0",
        paddingTop: "10px",
        marginBottom: "0"
    },
    cardCategoryWhite: {
        color: "rgba(" + hexToRgb(whiteColor) + ",.62)",
        margin: "0",
        fontSize: "14px",
        marginTop: "0",
        marginBottom: "0"
    },
    cardTitle: {
        color: grayColor[2],
        marginTop: "0px",
        minHeight: "auto",
        fontWeight: "300",
        fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
        marginBottom: "3px",
        textDecoration: "none",
        "& small": {
            color: grayColor[1],
            fontWeight: "400",
            lineHeight: "1"
        }
    },
    cardTitleWhite: {
        color: whiteColor,
        marginTop: "0px",
        minHeight: "auto",
        fontWeight: "300",
        fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
        marginBottom: "3px",
        textDecoration: "none",
        "& small": {
            color: grayColor[1],
            fontWeight: "400",
            lineHeight: "1"
        }
    }
};

export default generalTileStyles;