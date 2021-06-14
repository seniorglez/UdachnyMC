package com.seniorglez.aplication.endpoints.post;

import com.seniorglez.aplication.login.GenerateToken;
import com.seniorglez.aplication.login.LoginUser;
import com.seniorglez.aplication.login.QueryUser;
import com.seniorglez.domain.model.EndpointResponse;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.TokenManager;
import com.seniorglez.infra.UserJDBC;

public class PostLogin {

    public EndpointResponse execute(QueryUser queryUser) {
        Result<User, UserErrors> loginResult = new LoginUser(new UserJDBC()).execute(queryUser);
        if (loginResult instanceof Result.Success) {
            String token = new GenerateToken(new TokenManager()).execute(queryUser);
            return new EndpointResponse(200, "application/json", "{\"result\":\" "+ token +" \"}");
        }
        switch ((UserErrors) ((Result.Failure) loginResult).getError()) {
            case SERVER_ERROR:
                return new EndpointResponse(500, "application/json", "{\n\"result\":\"SERVER ERROR\"\n}");
            case TIMEOUT:
                return new EndpointResponse(503, "application/json", "{\n\"result\":\"TIMEOUT\"\n}");
            case NO_RESPONSE:
                return new EndpointResponse(503, "application/json", "{\n\"result\":\"NO RESPONSE\"\n}");
            case PASSWORD_NULL:
                return new EndpointResponse(400, "application/json", "{\n\"result\":\"PASSWORD NULL\"\n}");
            case USERNAME_NULL:
                return new EndpointResponse(400, "application/json", "{\n\"result\":\"USERNAME NULL\"\n}");
            case USER_DOES_NOT_EXIST:
                return new EndpointResponse(401, "application/json", "{\n\"result\":\"USER DOES NOT EXIST\"\n}");
            default:
                return new EndpointResponse(0, "application/json", "{\n\"result\":\"UNTRACKED ERROR\"\n}");
        }
    }
}
