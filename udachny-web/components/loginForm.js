import { useState } from 'react';
import { isLogged } from '../lib/user';
import { useRouter } from 'next/router'

const axios = require('axios');

export function LoginForm({ color = "asdas" }) { //, children, ...others

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    //const [isLoggedIn, setIsLoggedIn] = useState(false);
    const router = useRouter()
    
    if(isLogged()) router.push('/profile')

    const handleClick = (e, user, password) => { //curl: curl -d "{username: 'guest', password: 'guest'}" -X POST http://localhost:4567/request_token

        e.preventDefault();

        const url = "/api/loginBridge"

        const data = {
            username: user,
            password: password
        }

        console.log('Request login with' + JSON.stringify(data))

        const config = {
            method: 'post',
            url,
            data: data,
            responseType: 'json'
        }
        axios(config)
            .then(function (response) {
                if (isLogged()) {
                    if(isLogged) router.push('/profile') //maybe this could be improved
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
            <button className={"login-form-button"} onClick={(e) => handleClick(e, username, password)}>login</button>
        </form>
    )
}