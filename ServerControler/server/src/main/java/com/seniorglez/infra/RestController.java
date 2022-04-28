package com.seniorglez.infra;

import com.google.gson.Gson;
import com.seniorglez.aplication.endpoints.post.PostGetJSON;
import com.seniorglez.aplication.endpoints.post.PostLogin;
import com.seniorglez.aplication.endpoints.post.PostLogs;
import com.seniorglez.aplication.endpoints.post.PostMinecraftCommand;
import com.seniorglez.aplication.endpoints.post.PostLastLogs;
import com.seniorglez.aplication.lifeCicle.RestartApplication;
import com.seniorglez.aplication.lifeCicle.UpdateServer;
import com.seniorglez.aplication.login.QueryUser;
import com.seniorglez.aplication.login.ValidateToken;
import com.seniorglez.domain.RestPort;
import com.seniorglez.domain.model.*;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.threadPool;

public class RestController extends RestPort { 

    private final Process mcProcess;
    private final Gson gson;
    private final PropertiesReader propertiesReader;

    public RestController(Process mcProcess, PropertiesReader propertiesReader) {
        this.mcProcess = mcProcess;
        this.gson = new Gson();
        this.propertiesReader = propertiesReader;
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

    @Override
    protected void mapMainEndpoint() {
        get("/", (request,response)->{
            response.status(200);
            response.type("application/json");
            /* UNSCAPED JSON
            {
                "request_jwt": "/request_token",
                "send_mc_command": "/mc",
                "update_server": "/update",
                "get_mc_logs": "/logs",
                "get_json": "/get_json",
                "get_world": "/world",
                "get_last_logs": "/last_logs"
            }
            */
            return "{\r\n  \"request_jwt\": \"/request_token\",\r\n  \"send_mc_command\": \"/mc\",\r\n  \"update_server\": \"/update\",\r\n  \"get_mc_logs\": \"/logs\",\r\n  \"get_json\": \"/get_json\",\r\n  \"get_world\": \"/world\",\r\n  \"get_last_logs\": \"/last_logs\"\r\n}";
        });
    }

    @Override
    protected void mapPostMCEndpoint() {
        post("/mc", (request, response) -> {
            CommandRequest commandRequest = gson.fromJson(request.body(), CommandRequest.class);
            EndpointResponse res = new PostMinecraftCommand(this.mcProcess).execute(commandRequest);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });
    }

    @Override
    protected void mapPostRequestTokenEndpoint() {
        post("/request_token", ((request, response) -> {
            QueryUser queryUser = gson.fromJson(request.body(), QueryUser.class);
            EndpointResponse res = new PostLogin().execute(queryUser);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        }));

    }

    @Override
    protected void mapPostUpdateEndpoint() {
        post("/update", (request, response) -> {
            String token = request.queryParams("token");
            if (new ValidateToken(new TokenManager()).execute(token)) {
                new Thread(() -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new RestartApplication();
                }).start();
                new UpdateServer(System.getProperty("user.home") + "minecraft-server/server.jar").execute(); // this is
                                                                                                             // lame
                response.status(200);
                return "Updating...";
            } else {
                response.status(403);
                return "The token is not valid";
            }
        });

    }

    @Override
    protected void mapPostGetLogsEndpoint() {
        post("/logs", (request, response) -> {
            final String filename = "logs";
            String token = request.queryParams("token");
            EndpointResponse res = new PostLogs(new Zippo()).execute(token, "/root/" + filename,
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

    @Override
    protected void mapPostGetServerJSON() {
        post("/get_json", (request, response) -> {
            JSONRequest jsonRequest = gson.fromJson(request.body(), JSONRequest.class);
            EndpointResponse res = new PostGetJSON(new FileReaderImpl()).execute( jsonRequest.getToken(), jsonRequest.getFileName());
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });
        
    }

    @Override
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

    @Override
    protected void mapGetLastLogLines() {
        post("/last_logs", (request, response) -> {
            LogLineRequest logLineRequest = gson.fromJson(request.body(), LogLineRequest.class);
            EndpointResponse res = new PostLastLogs(new TailReader()).execute(logLineRequest);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });
    }
}