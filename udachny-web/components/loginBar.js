import Link from 'next/link'
import { isLogged } from '../lib/user';
import useSWR from 'swr'

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
    const { data, error } = useSWR('/api/user/123', fetcher)
    if(error) return(<Link href="/login">LOGIN</Link>)  
    return(<a>LOGOUT</a>)
}
