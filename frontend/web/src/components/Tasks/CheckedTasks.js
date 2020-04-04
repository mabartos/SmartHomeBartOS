import React from "react";
import classnames from "classnames";
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
import Checkbox from "@material-ui/core/Checkbox";
import Table from "@material-ui/core/Table";
import TableRow from "@material-ui/core/TableRow";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
// @material-ui/icons
import Check from "@material-ui/icons/Check";
// core components
import styles from "../../assets/jss/material-dashboard-react/components/tasksStyle.js";
import NoItemsAvailable from "../BartCard/NoItemsAvailable";
import {Clickable} from "react-clickable";

const useStyles = makeStyles(styles);

export default function CheckedTasks(props) {
    const classes = useStyles();
    const [checked, setChecked] = React.useState([]);

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
    const {tasks, rtlActive, checkedCallback} = props;
    const tableCellClasses = classnames(classes.tableCell, {
        [classes.tableCellRTL]: rtlActive
    });

    const getAllTasks = () => (

        [...tasks].map(([key, value], index) => (
                <TableRow key={index} className={classes.tableRow}>
                    <Clickable onClick={() => handleToggle(key)}>

                        <TableCell className={tableCellClasses}>
                            <Checkbox
                                checked={checked.indexOf(key) !== -1}

                                tabIndex={-1}
                                checkedIcon={<Check className={classes.checkedIcon}/>}
                                icon={<Check className={classes.uncheckedIcon}/>}
                                classes={{
                                    checked: classes.checked,
                                    root: classes.root
                                }}
                            />
                        </TableCell>
                        <TableCell className={tableCellClasses}>{value.name}</TableCell>
                    </Clickable>

                </TableRow>
            )
        )
    );
            const printAllTasks = tasks.size === 0 ? <NoItemsAvailable message={"No items found"}/> : getAllTasks();

    return (
        <div>
            <Table className={classes.table}>
                <TableBody>
                    {printAllTasks}
                </TableBody>
            </Table>
        </div>
    );
};

