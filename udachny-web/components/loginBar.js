import Link from 'next/link';
import { isLogged, deleteToken } from '../lib/user';
import Router from 'next/router';
import useSWR from 'swr';

export function LoginBar({children}) {
    
    return (
        <div className={'login-bar'}>
            <div className={'login-bar-container'}>
                <div className={'login-bar-element'}>
                        <LoginLogout/>
                </div>
            </div>
        </div>
    )
}


const fetcher = (...args) => isLogged();

const handleClick = (e) => {
    e.preventDefault();
    deleteToken();
    Router.reload(window.location.pathname);
}

var LoginLogout = () => {
    console.log('login,logout');
    const { data, error } = useSWR('cookie', fetcher, { refreshInterval: 1000 }) //To show the correct text when login, maybe not the best solution
    if(data) return(<a onClick={(e) => handleClick(e)} >LOGOUT</a>)
    return(<Link href="/login">LOGIN</Link>); 
}
