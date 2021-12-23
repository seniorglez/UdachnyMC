import Link from 'next/link'

export function LoginBar({children}) {
    return (
        <div className={'login-bar'}>
            <div className={'login-bar-container'}>
                <div className={'login-bar-element'}>
                    <Link href="/login">LOGIN</Link>
                </div>
            </div>
        </div>
    )
}