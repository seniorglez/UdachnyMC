export function LoginForm({color="asdas"}) { //, children, ...others
    return (
        <form className={"login-form"}>
            <input type="text" placeholder="User"/>
            <input type="password" placeholder="Password"/>
            <input className={"login-form-button"} type="submit" value="login"/>
        </form>
    )
}