import React, { useState } from 'react';
import { SERVER_URL } from '../constants.js';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import CarList from './CarList.js';
import { Snackbar } from '@material-ui/core';

function Login(){

    const [user, setUser] = useState({
        username:'',
        password:''
    });

    const [isAuthenticated, setAuth] = useState(false);

    const [open, setOpen] = useState(false);

    const handleChange = (event) => {
        setUser({...user, [event.target.name] : event.target.value});
    }

    const login = () =>{
        fetch(SERVER_URL + 'login', {
            method: 'POST',
            headers: { 'Content-Type':'application/json'},
            body: JSON.stringify(user)
        })
        .then(res => {
            const jwtToken = res.headers.get('Authorization');
            if(jwtToken !== null){
                sessionStorage.setItem("jwt", jwtToken);
                setAuth(true);
            }
        })
        .catch(err => console.error(err))
    }

    if(isAuthenticated){
        return <CarList />;
    } else {
        return(

            <div>
                <Stack spacing={2} alignItems='center' mt={2}>
                    <TextField
                        name="username"
                        label="Username"
                        onChange={handleChange} />
                    <TextField
                        type="password"
                        name="password"
                        label="password"
                        onChange={handleChange} />
                    <Button
                        variant="outlined"
                        color="primary"
                        onClick={login}>
                        Login
                    </Button>
                </Stack>
                <Snackbar
                    open={open}
                    autoHideDuration={3000}
                    onClose={() => setOpen(false)}
                    message="Login failed: Check your username and password"
                />
            </div>
        );
    }

}

export default Login;