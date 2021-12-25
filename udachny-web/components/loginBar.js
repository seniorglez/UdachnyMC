import Link from 'next/link';
import { isLogged, deleteToken } from '../lib/user';
import { useRouter } from 'next/router';
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

var LoginLogout = () => {

    const router = useRouter()

    const handleClick = (e) => {
        e.preventDefault();
        deleteToken();
        router.push('/')
    }

    const { data, error } = useSWR('cookie', fetcher, { refreshInterval: 1000 }) //To show the correct text when login, maybe not the best solution
    if(data) return(<a onClick={(e) => handleClick(e)} >LOGOUT</a>)
    return(<Link href="/login">LOGIN</Link>); 
}
