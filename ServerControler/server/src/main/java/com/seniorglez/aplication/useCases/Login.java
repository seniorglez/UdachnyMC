package com.seniorglez.aplication.useCases;

import com.seniorglez.aplication.useCases.loginUser.LoginUserInput;
import com.seniorglez.aplication.useCases.loginUser.LoginUserOutput;
import com.seniorglez.aplication.useCases.loginUser.LoginUserUseCase;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;

public class Login {

    public EndpointResponse execute(LoginUserInput queryUser) {
        LoginUserOutput output = new LoginUserUseCase().execute(queryUser);
        Result<String, UserErrors> loginResult = output.getResult();
        if (loginResult instanceof Result.Success) {
            return new EndpointResponse(200, "application/json", "{\"result\":\" "+ ((Result.Success<String, ?>) loginResult).getValue() +" \"}");
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
