package com.seniorglez.infra.api.v1.response;

import spark.Response;

public class ResponseApplyer {

    public static void apply(EndpointResponse endpointResponse, Response response) {
        response.status(endpointResponse.getResponseCode());
        response.type(endpointResponse.getResponseType());
        response.body(endpointResponse.getResponseBody());
    }

}
