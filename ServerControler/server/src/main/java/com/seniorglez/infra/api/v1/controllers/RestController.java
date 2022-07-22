package com.seniorglez.infra.api.v1.controllers;

import com.google.gson.Gson;
import com.seniorglez.aplication.lifeCicle.RestartApplication;
import com.seniorglez.aplication.useCases.GetLastLogs;
import com.seniorglez.aplication.useCases.GetLogs;
import com.seniorglez.aplication.useCases.PostMinecraftCommand;
import com.seniorglez.aplication.useCases.getJSON.GetJSONUseCase;
import com.seniorglez.domain.TokenManager;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.infra.api.v1.requests.command.CommandRequest;
import com.seniorglez.infra.api.v1.requests.json.JSONRequest;
import com.seniorglez.infra.api.v1.requests.log.LogLineRequest;
import com.seniorglez.infra.api.v1.auth.TokenManagerImpl;
import com.seniorglez.infra.fileManagement.FileReaderImpl;
import com.seniorglez.infra.fileManagement.PropertiesReader;
import com.seniorglez.infra.fileManagement.TailReader;
import com.seniorglez.infra.fileManagement.Zippo;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.threadPool;

public class RestController { 

    private final Process mcProcess;
    private final Gson gson;
    private final PropertiesReader propertiesReader;

    private final TokenManager tokenManager;

    public RestController(Process mcProcess, PropertiesReader propertiesReader) {
        this.mcProcess = mcProcess;
        this.gson = new Gson();
        this.propertiesReader = propertiesReader;
        this.tokenManager = new TokenManagerImpl();
        sparkConfiguration(this.propertiesReader);
    }

    private void sparkConfiguration(PropertiesReader propertiesReader) {
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Not found\"}";
        });
        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Internal Server Error\"}";
        });
        // getProperty returns null wtf...
        //int maxThreads = Integer.parseInt( propertiesReader.getProperty("maxThreads "));
        //int minThreads = Integer.parseInt( propertiesReader.getProperty("minThreads"));
        //int timeOutMillis = Integer.parseInt( propertiesReader.getProperty("timeOutMillis"));
        //threadPool(maxThreads, minThreads, timeOutMillis);
        threadPool(8, 3, 30000);
    }

    protected void mapPostMCEndpoint() {
        post("/mc", (request, response) -> {
            CommandRequest commandRequest = gson.fromJson(request.body(), CommandRequest.class);
            EndpointResponse res = new PostMinecraftCommand(this.mcProcess).execute(commandRequest);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });
    }

    protected void mapPostGetLogsEndpoint() {
        post("/logs", (request, response) -> {
            final String filename = "logs";
            String token = request.queryParams("token");
            EndpointResponse res = new GetLogs(new Zippo()).execute(token, "/root/" + filename,
                    "/tmp/" + filename + ".zip");
            if (res.getResponseCode() == 200) {
                response.header("Content-disposition", "attachment; filename=" + filename + ".zip;");
                File file = new File("/tmp/" + filename + ".zip");
                OutputStream outputStream = response.raw().getOutputStream();
                outputStream.write(Files.readAllBytes(file.toPath()));
                outputStream.flush();
                return response;
            }
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });

    }


    protected void mapPostGetServerJSON() {
        post("/get_json", (request, response) -> {
            JSONRequest jsonRequest = gson.fromJson(request.body(), JSONRequest.class);
            if(tokenManager.validate(jsonRequest.getToken())) {
                response.status(401);
                response.type("JSON");
                response.body("{}");
                return response;
            }
            EndpointResponse res = new GetJSONUseCase(new FileReaderImpl()).execute( jsonRequest.getToken(), jsonRequest.getFileName());
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });
        
    }


    protected void mapGetWorldEndpoint() {
        get("/world", ((request, response) -> {
            final String filename = "world";
            response.header("Content-disposition", "attachment; filename=" + filename + ".zip;");
            new Zippo().zipDir("/root/" + filename, "/tmp/" + filename + ".zip");
            File file = new File("/tmp/" + filename + ".zip");
            OutputStream outputStream = response.raw().getOutputStream();
            outputStream.write(Files.readAllBytes(file.toPath()));
            outputStream.flush();
            return response;
        }));

    }


    protected void mapGetLastLogLines() {
        post("/last_logs", (request, response) -> {
            LogLineRequest logLineRequest = gson.fromJson(request.body(), LogLineRequest.class);
            EndpointResponse res = new GetLastLogs(new TailReader()).execute(logLineRequest);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });
    }
}