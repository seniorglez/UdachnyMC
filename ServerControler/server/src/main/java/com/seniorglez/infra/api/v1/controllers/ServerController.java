package com.seniorglez.infra.api.v1.controllers;

import com.seniorglez.aplication.useCases.server.getServerVersion.GetServerVersionOutput;
import com.seniorglez.aplication.useCases.server.getServerVersion.GetServerVersionUseCase;
import com.seniorglez.aplication.useCases.server.startServer.StartServerOutput;
import com.seniorglez.aplication.useCases.server.startServer.StartServerUseCase;
import com.seniorglez.aplication.useCases.server.stopServerUseCase.StopServerOutput;
import com.seniorglez.aplication.useCases.server.stopServerUseCase.StopServerUseCase;
import com.seniorglez.aplication.useCases.server.updateServerVersion.UpdateServerVersionInput;
import com.seniorglez.aplication.useCases.server.updateServerVersion.UpdateServerVersionUseCase;
import com.seniorglez.infra.api.v1.auth.AuthFilter;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.infra.api.v1.response.ResponseApplyer;
import com.seniorglez.infra.api.v1.response.builder.GenericErrorResponseBuilder;
import spark.Request;
import spark.Response;
import static spark.Spark.before;
import static spark.Spark.put;
import static spark.Spark.get;

import static com.seniorglez.infra.api.v1.Global.AUTH_ENDPOINT_PREFIX;

public class ServerController {

    private UpdateServerVersionUseCase updateServerVersionUseCase = new UpdateServerVersionUseCase();

    private GetServerVersionUseCase getServerVersionUseCase = new GetServerVersionUseCase();

    private StartServerUseCase startServerUseCase = new StartServerUseCase();

    private StopServerUseCase stopServerUseCase = new StopServerUseCase();


    private final String ENDPOINT_PREFIX = "/v1/server";

    public void init() {

        // AUTH FILTER
        before(new AuthFilter(AUTH_ENDPOINT_PREFIX));

        put(ENDPOINT_PREFIX + "/version/:id", (request, response) -> updateVersionServer(request, response));

        get(ENDPOINT_PREFIX + "/version", (request, response) -> getServerVersion(request, response));

        put(ENDPOINT_PREFIX + "/power", (request, response) -> switchServerPower(request, response));

    }

    private Object switchServerPower(Request request, Response response) {
        String id = request.queryParams("powerStatus");
        switch (id) {
            case "0":
                StopServerOutput stopServerOutput = stopServerUseCase.execute();
                response.status(200);
                break;
            case "1":
                StartServerOutput startServerOutput = startServerUseCase.execute();
                response.status(200);
                break;
            default:
                EndpointResponse endpointResponse = GenericErrorResponseBuilder.getGenericErrorEndpointResponse(406, "Invalid input, should be between 0 and 1.");
                ResponseApplyer.apply(endpointResponse,response);
        }
        return response;
    }

    private Object updateVersionServer(Request request, Response response) {
        String id = request.params(":id");
        updateServerVersionUseCase.execute(new UpdateServerVersionInput(id));
        return response;
    }

    private Object getServerVersion(Request request, Response response) {
        GetServerVersionOutput output = getServerVersionUseCase.execute();
        return response;
    }
}