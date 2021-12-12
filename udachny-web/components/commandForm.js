import { useState } from 'react';
import { getToken } from '../lib/user';

const axios = require('axios');

export function CommandForm() { //curl -d "{command: 'say hola', token: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsImlhdCI6MTYzOTMyNTE4NSwiZXhwIjoxNjM5OTI5OTg1fQ.nBx81J1xDoTqxpUPWTbquM-xQZ7MOVzEYhYJz3UBQUo'}" -X POST http://localhost:4567/mc


    const [minecraftCommand, setCommand] = useState("");

    const handleClick = (e, command) => { //curl: curl -d "{c: 'guest', password: 'guest'}" -X POST http://localhost:4567/request_token

        e.preventDefault();

        const url = "/api/commandBridge"

        const data = {
            command: command,
            token: getToken()
        }

        console.log('Request command ' + JSON.stringify(data))

        const config = {
            method: 'post',
            url,
            data: data,
            responseType: 'json'
        }

        axios(config)
            .then(function (response) {
               console.log('Ok')
            })
            .catch(function (error) {
                console.log(error)
            })
    }

    return (
        <form className={"login-form"}>
            <input id="command" type="text" placeholder="Command" onChange={(e) => setCommand(e.target.value)} />
            <button className={"login-form-button"} onClick={(e) => handleClick(e, minecraftCommand)}>login</button>
        </form>
    )
}