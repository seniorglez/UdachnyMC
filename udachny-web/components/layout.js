import { FooterArea } from "./footerArea"
import { NavBar } from "./navBar"

export function Layout({children}) {
    return (
        <div>
            <NavBar/>
            {children}
            <FooterArea/>
        </div>
    )
}