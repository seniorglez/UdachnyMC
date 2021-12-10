import { useState } from 'react';
import { isLogged,getToken } from '../lib/user';

const axios = require('axios');

export function LoginForm({ color = "asdas" }) { //, children, ...others


    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleClick = (e, user, password) => { //curl: curl -d "{username: 'guest', password: 'guest'}" -X POST http://localhost:4567/request_token

        e.preventDefault();
        
        const url = "/api/loginBridge"

        const data = {
            username: user,
            password: password
        }

        const config = {
            method: 'post',
            url,
            data: data,
            responseType: 'json'
        }
        axios(config)
            .then(function (response) {
                console.log(getToken()) //debug
                if(isLogged()) {
                    console.log('Logged')
                } else {
                    console.log('F')
                }
            })
            .catch(function (error) {
                console.log(error)
            })

    }

    return (
        <form className={"login-form"}>
            <input id="userfield" type="text" placeholder="User" onChange={(e) => setUsername(e.target.value)} />
            <input id="passworldfield" type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
            <button className={"login-form-button"} onClick={(e) => handleClick(e, document.getElementById("userfield").value, document.getElementById("passworldfield").value)}>login</button>
        </form>
    )
}