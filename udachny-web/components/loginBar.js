import Link from 'next/link'

export function LoginBar({children}) {
    return (
        <div className={'login-bar'}>
            <div className={'login-bar-container'}>
                <Link href="/login">LOG IN</Link>
            </div>
        </div>
    )
}