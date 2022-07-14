package com.seniorglez.aplication.useCases.loginUser;


import com.seniorglez.aplication.services.UserService;
import com.seniorglez.aplication.services.impl.UserServiceImpl;
import com.seniorglez.domain.TokenManager;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.auth.TokenManagerImpl;

import static java.util.Objects.isNull;

public class LoginUserUseCase {

    private final TokenManager tokenManager;
    private final UserService userService;

    public LoginUserUseCase (UserService userService, TokenManager tokenManager) {
       this.userService = userService;
       this.tokenManager = tokenManager;
    }

    public LoginUserUseCase () {
        this.userService = new UserServiceImpl();
        this.tokenManager = new TokenManagerImpl();
    }

    public LoginUserOutput execute( LoginUserInput loginUserInput ) {

        final String USERNAME = loginUserInput.getUsername();
        final String PASSWORD = loginUserInput.getPassword();

        if (isNull(PASSWORD) || PASSWORD.isEmpty()) {
            return new LoginUserOutput(new Result.Failure< String,UserErrors >( UserErrors.PASSWORD_NULL ));
        } 

        if (isNull(USERNAME) || USERNAME.isEmpty()) {
            return new LoginUserOutput(new Result.Failure< String, UserErrors >( UserErrors.USERNAME_NULL ));
        }

        User user = userService.getUserFromCredentials(USERNAME, PASSWORD);

        if(!isNull(user)) {
            String token = tokenManager.getTokenFrom(user);
            return new LoginUserOutput(new Result.Success< String,UserErrors >( token ));
        }

        if( isNull(userService.getUserByUsername(USERNAME)) ) {
            return new LoginUserOutput(new Result.Failure< String, UserErrors >( UserErrors.USER_DOES_NOT_EXIST ));
        }

        return new LoginUserOutput(new Result.Failure< String, UserErrors >( UserErrors.WRONG_PASSWORD ));
    }
}
