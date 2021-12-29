import Link from "next/link"
import { LoginBar } from "./loginBar"

export function NavBar() {
  return (
    <header className="main-nav-wrapper">
      <LoginBar />

      <nav className="main-nav-bar">
        <ul className="main-nav-bar-links">
          <li>
            <Link href="/">
              <a>HOME</a>
            </Link>
          </li>
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
      </nav>
    </header>
  )
}
