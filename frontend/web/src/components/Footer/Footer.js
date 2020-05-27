/*eslint-disable*/
import React from "react";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
import ListItem from "@material-ui/core/ListItem";
import List from "@material-ui/core/List";
// core components
import styles from "assets/jss/material-dashboard-react/components/footerStyle.js";

const useStyles = makeStyles(styles);

export default function Footer(props) {
    const classes = useStyles();
    return (
        <footer className={classes.footer}>
            <div className={classes.container}>
                <div className={classes.left}>
                    <List className={classes.list}>
                        <ListItem className={classes.inlineBlock}>
                            <a href="/admin/home" className={classes.block}>
                                Home
                            </a>
                        </ListItem>
                        <ListItem className={classes.inlineBlock}>
                            <a href="/admin/homes" className={classes.block}>
                                My Homes
                            </a>
                        </ListItem>
                        <ListItem className={classes.inlineBlock}>
                            <a href="/admin/user" className={classes.block}>
                                User Profile
                            </a>
                        </ListItem>
                    </List>
                </div>
                <p className={classes.right}>
          <span>
            &copy; {1900 + new Date().getYear()}{" "}
            Martin Bartos
          </span>
                </p>
            </div>
        </footer>
    );
}
