package com.seniorglez.infra;

import com.google.gson.Gson;
import com.seniorglez.aplication.endpoints.post.PostLogin;
import com.seniorglez.aplication.endpoints.post.PostLogs;
import com.seniorglez.aplication.endpoints.post.PostMinecraftCommand;
import com.seniorglez.aplication.lifeCicle.RestartApplication;
import com.seniorglez.aplication.lifeCicle.UpdateServer;
import com.seniorglez.aplication.login.QueryUser;
import com.seniorglez.aplication.login.ValidateToken;
import com.seniorglez.aplication.sendMessage.SendMessage;
import com.seniorglez.domain.model.*;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

import static spark.Spark.get;
import static spark.Spark.post;

public class RestController {

    private final Process mcProcess;
    private final SendMessage sendMessage;
    private final Gson gson;

    public RestController(Process mcProcess, SendMessage sendMessage) {
        this.mcProcess = mcProcess;
        this.sendMessage = sendMessage;
        this.gson = new Gson();
    }

    public void start() {

        post("/mc", (request, response) -> {
            CommandRequest commandRequest = gson.fromJson(request.body(), CommandRequest.class);
            EndpointResponse res = new PostMinecraftCommand(this.mcProcess).execute(commandRequest);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        });

        post("/request_token", ((request, response) -> {
            QueryUser queryUser = gson.fromJson(request.body(), QueryUser.class);
            EndpointResponse res = new PostLogin().execute(queryUser);
            response.status(res.getResponseCode());
            response.type(res.getResponseType());
            return res.getResponseBody();
        }));

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
                new UpdateServer(System.getProperty("user.home") + "/server.jar").execute(); // this is lame
                response.status(200);
                return "Updating...";
            } else {
                response.status(403);
                return "The token is not valid";
            }
        });

        post("/get_logs", (request, response) -> {
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

        get("/hello", ((request, response) -> {
            return "world";
        }));
    }
}