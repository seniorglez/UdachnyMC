import Link from 'next/link'

export function NavBar() {
    return (
        <header className="nav-bar">
            <nav>
                <ul className="nav-bar-links">
                    <li><Link href="/"><a>HOME</a></Link></li>
                    <li><Link href="/login"><a>LogIn</a></Link></li>
                    <li><a href="https://github.com/seniorglez/UdachnyMC"
                    target="_blank"
                    rel="noopener noreferrer">GitHub</a></li>
                </ul>
            </nav>
        </header>
    )
}