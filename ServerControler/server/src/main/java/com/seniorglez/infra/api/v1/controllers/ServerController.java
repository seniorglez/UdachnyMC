package com.seniorglez.infra.api.v1.controllers;

import com.seniorglez.aplication.services.ServerService;
import com.seniorglez.aplication.services.impl.ServerServiceImpl;
import com.seniorglez.infra.api.v1.auth.AuthFilter;
import spark.Request;
import spark.Response;

import static com.seniorglez.infra.api.v1.Global.AUTH_ENDPOINT_PREFIX;
import static spark.Spark.*;

public class ServerController {

    private ServerService serverService = ServerServiceImpl.getInstance();

    private final String ENDPOINT_PREFIX = "/v1/server";

    public void init() {

        // AUTH FILTER
        before(new AuthFilter(AUTH_ENDPOINT_PREFIX));

        // LOGIN ENDPOINT
        put(ENDPOINT_PREFIX + "/update", (request, response) -> updateServer(request, response));

    }

    private Object updateServer(Request request, Response response) {
        serverService.updateServer();
        return response;
    }
}