import React from "react";
import PropTypes from "prop-types";
import classnames from "classnames";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import CheckIcon from '@material-ui/icons/Check';
import Check from '@material-ui/icons/Check';
// @material-ui/icons
// core components
import styles from "assets/jss/material-dashboard-react/components/tasksStyle.js";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import Checkbox from "@material-ui/core/Checkbox";
import Tooltip from "@material-ui/core/Tooltip";
import IconButton from "@material-ui/core/IconButton";
import Close from "@material-ui/icons/Close";
import NoItemsAvailable from "../BartCard/NoItemsAvailable";
import {useObserver} from "mobx-react-lite";

const useStyles = makeStyles(styles);

export default function Tasks(props) {
    const classes = useStyles();

    const [checked, setChecked] = React.useState([]);
    const [active, setActive] = React.useState(false);

    const {tasks, rtlActive, checkedCallback, activeCallback} = props;
    const tableCellClasses = classnames(classes.tableCell, {
        [classes.tableCellRTL]: rtlActive
    });

    const handleToggle = value => {
        const currentIndex = checked.indexOf(value);
        const newChecked = [...checked];

        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }
        setChecked(newChecked);
        checkedCallback(newChecked);
    };

    const handleAccept = (value) => {
        if (props.handleAccept !== undefined) {
            props.handleAccept(value);
        }
    };

    const handleDismiss = (value) => {
        if (props.handleDismiss !== undefined) {
            props.handleDismiss(value);
        }
    };

    const changeActivity = (state) => {
        if (state === true) {
            setActive(state);
        } else if (state === false) {
            setActive(state);
        }

        if (activeCallback !== undefined) {
            activeCallback(state);
        }
    };

    React.useEffect(() => {
        changeActivity(true);
        return () => {
            changeActivity(false);
        }
    }, []);


    const getAllTasks = () => (
        [...tasks].map(([key, value], index) => (
            <TableRow key={index} className={classes.tableRow}>
                <TableCell className={tableCellClasses}>
                    <Checkbox
                        checked={checked.indexOf(key) !== -1}
                        tabIndex={-1}
                        onClick={() => handleToggle(key)}
                        checkedIcon={<Check className={classes.checkedIcon}/>}
                        icon={<Check className={classes.uncheckedIcon}/>}
                        classes={{
                            checked: classes.checked,
                            root: classes.root
                        }}
                    />
                </TableCell>
                <TableCell className={tableCellClasses}>You've been invited to home '{value.homeName}'.</TableCell>
                <TableCell className={classes.tableActions}>
                    <Tooltip
                        id="tooltip-top"
                        title="Accept Invitation"
                        placement="top"
                        classes={{tooltip: classes.tooltip}}
                    >
                        <IconButton
                            aria-label="Accept"
                            className={classes.tableActionButton}
                            onClick={() => handleAccept(value.id)}
                        >
                            <CheckIcon
                                className={
                                    classes.tableActionButtonIcon + " " + classes.accept
                                }
                            />
                        </IconButton>
                    </Tooltip>
                    <Tooltip
                        id="tooltip-top-start"
                        title="Dismiss Invitation"
                        placement="top"
                        classes={{tooltip: classes.tooltip}}
                    >
                        <IconButton
                            aria-label="Dismiss"
                            className={classes.tableActionButton}
                            onClick={() => handleDismiss(value.id)}
                        >
                            <Close
                                className={
                                    classes.tableActionButtonIcon + " " + classes.close
                                }
                            />
                        </IconButton>
                    </Tooltip>
                </TableCell>
            </TableRow>
        ))
    );

    const printAllTasks = tasks.size === 0 ? <NoItemsAvailable message={"No items found"}/> : getAllTasks();

    return useObserver(() => {

        return (
            <Table className={classes.table}>
                <TableBody>
                    {printAllTasks}
                </TableBody>
            </Table>
        );
    });
}

Tasks.propTypes = {
    hideCheckBox: PropTypes.bool,
    checkedCallback: PropTypes.func
};
