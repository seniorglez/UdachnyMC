package com.seniorglez.infra.api.v1.response.builder;

import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.api.v1.response.EndpointResponse;

public class JWTResponseBuilder {

    public static EndpointResponse getJWTEndpointResponse(Result<String, UserErrors> result) {
        if(result instanceof Result.Failure) {
            Result.Failure<String, UserErrors> failure = (Result.Failure<String, UserErrors>) result;
            return UserErrorResponseBuilder.getUserErrorEndpointResponse(failure.getError());
        }
        Result.Success<String, UserErrors> success = (Result.Success<String, UserErrors>) result;
        return new EndpointResponse(
                200,
                "JSON",
                "{ \"jwt\":\"" + success.getValue() + "\" }");
    }
}
