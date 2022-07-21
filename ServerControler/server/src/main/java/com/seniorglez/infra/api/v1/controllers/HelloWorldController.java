package com.seniorglez.infra.api.v1.controllers;

import com.seniorglez.infra.auth.AuthFilter;
import spark.Request;
import spark.Response;

import static com.seniorglez.infra.api.v1.Global.AUTH_ENDPOINT_PREFIX;
import static spark.Spark.*;

public class HelloWorldController {

    public void init() {
        // AUTH FILTER
        before(new AuthFilter(AUTH_ENDPOINT_PREFIX));

        get(AUTH_ENDPOINT_PREFIX + "/hello_world", (request, response) -> helloWorld(request, response));
    }

    private Object helloWorld(Request request, Response response) {
        return "helloWorld";
    }
}
