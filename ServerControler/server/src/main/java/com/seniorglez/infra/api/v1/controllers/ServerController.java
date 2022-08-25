package com.seniorglez.infra.api.v1.controllers;

import com.seniorglez.aplication.useCases.server.getServerVersion.GetServerVersionUseCase;
import com.seniorglez.aplication.useCases.server.startServer.StartServerOutput;
import com.seniorglez.aplication.useCases.server.startServer.StartServerUseCase;
import com.seniorglez.aplication.useCases.server.stopServerUseCase.StopServerOutput;
import com.seniorglez.aplication.useCases.server.stopServerUseCase.StopServerUseCase;
import com.seniorglez.aplication.useCases.server.updateServerVersion.UpdateServerVersionInput;
import com.seniorglez.aplication.useCases.server.updateServerVersion.UpdateServerVersionUseCase;
import com.seniorglez.infra.api.v1.auth.AuthFilter;
import spark.Request;
import spark.Response;

import static com.seniorglez.infra.api.v1.Global.AUTH_ENDPOINT_PREFIX;
import static spark.Spark.*;

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
                break;
            case "1":
                StartServerOutput startServerOutput = startServerUseCase.execute();
                break;
            default:
                response.status(406);
                response.type("json"); //revisar
                return "";

        }
    }

    private Object updateVersionServer(Request request, Response response) {
        String id = request.params(":id");
        updateServerVersionUseCase.execute(new UpdateServerVersionInput(id));
        return response;
    }

    private Object getServerVersion(Request request, Response response) {
        serverService.getVersion();
        return response;
    }
}