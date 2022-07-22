package com.seniorglez.aplication.useCases.loginUser;

import com.seniorglez.aplication.services.UserService;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.api.v1.auth.TokenManagerImpl;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestLoginUserUseCase {

    private UserService mockUserService = new UserService() {

        @Override
        public User getUserFromCredentials(String username, String password) {
            if(username.equals("carlos") && password.equals("pwd")) {
                return new User("carlos","pwd");
            }
            return null;
        }

        @Override
        public User getUserByUsername(String username) {
            if(username.equals("carlos")) {
                return new User("carlos","pwd");
            }
            return null;
        }
    };

    @Test
    public void shouldLogIn()
    {
        LoginUserInput input = new LoginUserInput("carlos","pwd");
        LoginUserUseCase loginUserUseCase = new LoginUserUseCase(mockUserService, new TokenManagerImpl());
        LoginUserOutput output = loginUserUseCase.execute(input);
        Result<String, UserErrors> result = output.getResult();
        assertTrue(result instanceof Result.Success);
    }

    @Test
    public void shouldNotLogInIfUserDoesNotExist()
    {
        LoginUserInput input = new LoginUserInput("jon","pwd");
        LoginUserUseCase loginUserUseCase = new LoginUserUseCase(mockUserService, new TokenManagerImpl());
        LoginUserOutput output = loginUserUseCase.execute(input);
        Result<String, UserErrors> result = output.getResult();
        assertTrue(result instanceof Result.Failure);
    }

    @Test
    public void shouldNotLogInIfUserExistButThePasswordDoesNotMatch()
    {
        LoginUserInput input = new LoginUserInput("carlos","papopepo");
        LoginUserUseCase loginUserUseCase = new LoginUserUseCase(mockUserService, new TokenManagerImpl());
        LoginUserOutput output = loginUserUseCase.execute(input);
        Result<String, UserErrors> result = output.getResult();
        assertTrue(result instanceof Result.Failure);
    }

    @Test
    public void shouldNotLoginIfUserIsNull()
    {
        LoginUserInput input = new LoginUserInput(null,"papopepo");
        LoginUserUseCase loginUserUseCase = new LoginUserUseCase(mockUserService, new TokenManagerImpl());
        LoginUserOutput output = loginUserUseCase.execute(input);
        Result<String, UserErrors> result = output.getResult();
        assertTrue(result instanceof Result.Failure);
    }

    @Test
    public void shouldNotLoginIfPasswordIsNull()
    {
        LoginUserInput input = new LoginUserInput("carlos",null);
        LoginUserUseCase loginUserUseCase = new LoginUserUseCase(mockUserService, new TokenManagerImpl());
        LoginUserOutput output = loginUserUseCase.execute(input);
        Result<String, UserErrors> result = output.getResult();
        assertTrue(result instanceof Result.Failure);
    }

    @Test
    public void shouldNotLoginIfPasswordAndUserIsNull()
    {
        LoginUserInput input = new LoginUserInput(null,null);
        LoginUserUseCase loginUserUseCase = new LoginUserUseCase(mockUserService, new TokenManagerImpl());
        LoginUserOutput output = loginUserUseCase.execute(input);
        Result<String, UserErrors> result = output.getResult();
        assertTrue(result instanceof Result.Failure);
    }

}
