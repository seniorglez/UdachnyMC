export function LoginForm({color="asdas"}) { //, children, ...others
    return (
        <form className={"login-form"} action="/" method="POST">
            <input type="text" placeholder="user name"/>
            <input type="password" placeholder="password"/>
            <input className={"login-form-button"} type="submit" value="login"/>
        </form>
    )

}