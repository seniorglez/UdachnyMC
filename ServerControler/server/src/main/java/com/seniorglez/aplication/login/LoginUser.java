package com.seniorglez.aplication.login;


import com.seniorglez.domain.Users;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;

public class LoginUser {

    private final Users users;

    public LoginUser ( Users users ) {
       this.users = users;
    }

    public Result< User, UserErrors > execute( QueryUser queryUser ) {
        String username = queryUser.getUsername();
        String password = queryUser.getPassword();
        if ( password.isEmpty() || password == null ) return new Result.Failure< User,UserErrors >( UserErrors.PASSWORD_NULL );
        if ( username.isEmpty() || username == null  )
            return new Result.Failure< User, UserErrors >( UserErrors.USERNAME_NULL );
        Result< User, UserErrors > result = this.users.getUserByUsername( username );
        if ( result instanceof Result.Failure ) return result;
        User user = ( User )(( Result.Success ) result).getValue();
        if( user.getPassword().equals(password) ) return result;
        return new Result.Failure< User, UserErrors >( UserErrors.USER_DOES_NOT_EXIST );
    }
}
