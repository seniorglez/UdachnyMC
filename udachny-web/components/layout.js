import { NavBar } from "./navBar"

export function Layout({children}) {
    return (
        <div>
            <NavBar/>
            {children}
        </div>
    )
}