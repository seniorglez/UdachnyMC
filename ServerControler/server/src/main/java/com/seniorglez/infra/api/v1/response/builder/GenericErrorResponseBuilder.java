package com.seniorglez.infra.api.v1.response.builder;

import com.seniorglez.infra.api.v1.response.EndpointResponse;

public class GenericErrorResponseBuilder {

    public static EndpointResponse getGenericErrorEndpointResponse(int code, String message) {
        return new EndpointResponse(
                code,
                "JSON",
                "{ \"error\":\"" + message + "\" }");
    }
}
