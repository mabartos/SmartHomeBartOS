import React, {useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import {useHistory} from 'react-router-dom';
import {makeStyles} from '@material-ui/core/styles';
import loginStyle from '../../assets/jss/login/loginStyle';
import useStores from "../../hooks/useStores";
import ErrorNotification from "../../components/Notifications/ErrorNotification";
import SuccessNotification from "../../components/Notifications/SuccessNotification";
import {useObserver} from "mobx-react-lite";
import {SemipolarLoading} from "react-loadingg";

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
        </Typography>
    );
}

const useStyles = makeStyles(loginStyle);

export default function LoginPage() {
    const classes = useStyles();
    const history = useHistory();

    const {authStore} = useStores();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    return useObserver(() => {
        const {error, loading, actionInvoked, user,isUserLogged} = authStore;


        const verifyLogin = () => {
            authStore.login(username, password);
            if (isUserLogged) {
                history.push("/admin");
            }
        };

        const manageKeys = (e) => {
            if (e.key === 'Enter') {
                verifyLogin();
            }
        };

        return (
            <div>
                {error && <ErrorNotification message={"Bad credentials."} showLoading={false}/>}
                {actionInvoked && <SuccessNotification message={actionInvoked}/>}
                {loading && <SemipolarLoading/>}
                <Grid container component="main" className={classes.root}>

                    <CssBaseline/>
                    <Grid item xs={false} sm={4} md={7} className={classes.image}/>
                    <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square onKeyDown={manageKeys}>
                        <div className={classes.paper}>
                            <Avatar className={classes.avatar}>
                                <LockOutlinedIcon/>
                            </Avatar>
                            <Typography component="h1" variant="h5">
                                Sign in
                            </Typography>
                            <form className={classes.form} noValidate>
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    required
                                    fullWidth
                                    id="username"
                                    label="Username"
                                    name="username"
                                    onChange={(e) => setUsername(e.target.value)}
                                    autoComplete="username"
                                    autoFocus
                                    error={Boolean(error)}
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    required
                                    fullWidth
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    onChange={(e) => setPassword(e.target.value)}
                                    autoComplete="current-password"
                                />
                                {/* <FormControlLabel
                                    control={<Checkbox value="remember" color="primary"/>}
                                    label="Remember me"
                                />*/}
                                <Button
                                    fullWidth
                                    variant="contained"
                                    color="primary"
                                    className={classes.submit}
                                    onClick={verifyLogin}
                                    onKeyDown={manageKeys}
                                >
                                    Sign In
                                </Button>

                                <Grid item>
                                    <Link href="/signup" variant="body2">
                                        {"Don't have an account? Sign Up"}
                                    </Link>
                                </Grid>
                                <Box mt={5}>
                                    <Copyright/>
                                </Box>
                            </form>
                        </div>
                    </Grid>
                </Grid>
            </div>
        );
    });

};