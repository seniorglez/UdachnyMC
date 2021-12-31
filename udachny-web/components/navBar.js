import Link from "next/link"
import { LoginBar } from "./loginBar"

export function NavBar() {
  return (
    <header className="main-nav-wrapper">

      <nav className="main-nav-bar">

        <ul className="main-nav-bar-home">
          <li>
            <Link href="/">
              <a><img src="/logo.png" width={"60px"}></img></a>
            </Link>
          </li>
        </ul>

        <ul className="main-nav-bar-links">
          <li>
            <a
              href="https://github.com/seniorglez/UdachnyMC"
              target="_blank"
              rel="noopener noreferrer"
            >
              GitHub
            </a>
          </li>
          <li>
            <a href="http://localhost:4567/world">Descargar mundo</a>
          </li>
        </ul>

        <ul className="main-nav-bar-links nav-bar-login">
          <li>
            <LoginBar />
          </li>
        </ul>

      </nav>

    </header>
  )
}
