package com.seniorglez.infra.api.v1.controllers;

import com.seniorglez.infra.api.v1.auth.AuthFilter;
import spark.Request;
import spark.Response;

import static com.seniorglez.infra.api.v1.Global.AUTH_ENDPOINT_PREFIX;
import static spark.Spark.*;

public class HelloWorldController {

    private String ENDPOINT_PREFIX = "/v1/hello-world";

    public void init() {
        // AUTH FILTER
        before(new AuthFilter(AUTH_ENDPOINT_PREFIX));

        get(ENDPOINT_PREFIX , (request, response) -> getHelloWorld(request, response));
    }

    private Object getHelloWorld(Request request, Response response) {
        return "helloWorld";
    }
}
