import { useState } from 'react';
import { getToken, deleteToken, isLogged } from '../lib/user';
import { useRouter } from 'next/router'

const axios = require('axios');

export function CommandForm() { //curl -d "{command: 'say hola', token: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsImlhdCI6MTYzOTMyNTE4NSwiZXhwIjoxNjM5OTI5OTg1fQ.nBx81J1xDoTqxpUPWTbquM-xQZ7MOVzEYhYJz3UBQUo'}" -X POST http://localhost:4567/mc


    const [minecraftCommand, setCommand] = useState("");
    const router = useRouter()

    const handleClick = (e, command) => { //curl: curl -d "{c: 'guest', password: 'guest'}" -X POST http://localhost:4567/request_token

        e.preventDefault();

        if (!isLogged) router.push('/login')

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
                console.log('OK')
                document.getElementById("command").value = "";
            })
            .catch(function (error) { //When I try to access the status, it returns an undefined, so I'm going to assume that it is a 4 ** error and delete the token.
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                  } 
                    console.log('deleting token')
                    deleteToken()
                    router.push('/login')
                  
                
            })
    }

    return (
        <form className={"command-form"}> 
            <div className={"command-form-area"}>Loading ...</div>
            <input id="command" className={"command-form-text"} type="text" placeholder="Command" onChange={(e) => setCommand(e.target.value)} />
            <button className={"comand-form-button"} onClick={(e) => handleClick(e, minecraftCommand)}>send</button>
        </form>
    )
}