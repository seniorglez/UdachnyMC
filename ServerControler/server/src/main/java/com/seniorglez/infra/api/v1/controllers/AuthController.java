package com.seniorglez.infra.api.v1.controllers;

import com.google.gson.Gson;
import com.seniorglez.aplication.useCases.loginUser.LoginUserInput;
import com.seniorglez.aplication.useCases.loginUser.LoginUserOutput;
import com.seniorglez.aplication.useCases.loginUser.LoginUserUseCase;
import com.seniorglez.domain.TokenManager;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.api.v1.requests.user.LogInRequest;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.infra.api.v1.response.builder.JWTResponseBuilder;
import com.seniorglez.infra.auth.AuthFilter;
import com.seniorglez.infra.auth.TokenManagerImpl;
import spark.Request;
import spark.Response;

import static spark.Spark.before;
import static spark.Spark.post;

public class AuthController {

    private static final String AUTH_ENDPOINT_PREFIX = "/auth";

    private final Gson GSON = new Gson();

    private TokenManager tokenManager = new TokenManagerImpl();

    private LoginUserUseCase loginUserUseCase = new LoginUserUseCase();

    public void init() {

        // AUTH FILTER
        before(new AuthFilter(AUTH_ENDPOINT_PREFIX));

        // LOGIN ENDPOINT
        post(AUTH_ENDPOINT_PREFIX + "/login", (request, response) -> login(request, response));

    }
    private String login(Request request, Response response) {
        LogInRequest logInRequest = GSON.fromJson(request.body(), LogInRequest.class);
        LoginUserInput queryUser = logInRequest.toLoginUserInput();
        LoginUserOutput loginUserOutput = loginUserUseCase.execute(queryUser);
        Result<String, UserErrors> result = loginUserOutput.getResult();
        EndpointResponse res = JWTResponseBuilder.getJWTEndpointResponse(result);
        response.status(res.getResponseCode());
        response.type(res.getResponseType());
        return res.getResponseBody();
    }

}
