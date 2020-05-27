import React from 'react';
import Button from '@material-ui/core/Button';
import Link from '@material-ui/core/Link';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import {makeStyles} from '@material-ui/core/styles';
import loginStyle from '../../assets/jss/login/loginStyle';
import {useObserver} from "mobx-react-lite";
import logo from "../../assets/img/logo.png";
import GridContainer from "../../components/Grid/GridContainer";
import GridItem from "../../components/Grid/GridItem";
import {useHistory} from "react-router-dom";
import BartOSTitle from "../../components/Typography/BartOSTitle";

function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="">
                SmartHome Bartos
            </Link>
            {' '}
            {new Date().getFullYear()}
            {'.'}
            Picture designed by <a href="http://www.freepik.com">Freepik</a>
        </Typography>
    );
}

const useStyles = makeStyles(loginStyle);
const useMoreStyles = makeStyles((theme) => ({
    header: {
        textAlign: "center",
        justifyContent: "center",
        padding: "20px",
    },
    title: {
        textAlign: "center",
    },
    logo: {
        height: "11vh"
    },
    copyright: {
        marginTop: "30%"
    },
    caption: {
        marginTop: "20%"
    },
    buttons: {
        top: "50vh",
        width: "100%",
        padding: "20px"
    },
    button: {
        left: "-20px",
        height: "70px",
        width: "100%",
        marginTop: "70%",
        margin: theme.spacing(1),
    }
}));

export default function LoginPage() {
    const classes = useStyles();
    const styles = useMoreStyles();
    const history = useHistory();

    return useObserver(() => {

        const handleLogin = () => {
            history.push("/admin");
        };

        return (
            <div>
                <Grid container component="main" className={classes.root}>
                    <Grid item xs={false} sm={4} md={7} className={classes.image}/>
                    <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                        <div className={classes.paper} >
                            <GridContainer classname={styles.header}>
                                <GridItem>
                                    <img className={styles.logo} src={logo} alt={"logo"}/>
                                </GridItem>
                                <GridItem >
                                    <Typography className={styles.title} component="h1" variant="h4">
                                        SmartHome
                                        <BartOSTitle subVariant={"h2"} variant={"h2"}/>
                                    </Typography>
                                </GridItem>
                            </GridContainer>

                            <Typography className={styles.caption} component="caption" variant={"h4"}>
                                Comprehensive Smart Home solution for
                                <Typography component={"caption"} variant={"h3"} color={"primary"}> EVERYONE.
                                </Typography>
                            </Typography>

                            <GridContainer className={styles.buttons}>
                                <GridItem xs={12}>
                                    <Button className={styles.button} size="large" variant="outlined" color="secondary"
                                            onClick={handleLogin}>
                                        Enter to Application
                                    </Button>
                                </GridItem>
                            </GridContainer>
                            <Box className={styles.copyright} mt={5}>
                                <Copyright/>
                            </Box>
                        </div>
                    </Grid>
                </Grid>
            </div>
        );
    });

};