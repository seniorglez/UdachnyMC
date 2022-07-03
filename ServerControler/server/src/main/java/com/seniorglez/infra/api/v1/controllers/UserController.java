package com.seniorglez.infra.api.v1.controllers;

import static spark.Spark.post;

import com.google.gson.Gson;
import com.seniorglez.aplication.useCases.loginUser.LoginUserInput;
import com.seniorglez.aplication.useCases.loginUser.LoginUserOutput;
import com.seniorglez.aplication.useCases.loginUser.LoginUserUseCase;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.infra.api.v1.response.builder.JWTResponseBuilder;


public class UserController {

    private final Gson GSON = new Gson();

    private final LoginUserUseCase loginUserUseCase = new LoginUserUseCase();

    public void start() {
        post("v1/request_token", ((request, response) -> {
            LoginUserInput queryUser = GSON.fromJson(request.body(), LoginUserInput.class);
            LoginUserOutput loginUserOutput = loginUserUseCase.execute(queryUser);
            Result<String, UserErrors> result = loginUserOutput.getResult();
            EndpointResponse res = JWTResponseBuilder.getJWTEndpointResponse(result);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        }));
    }
    
}
