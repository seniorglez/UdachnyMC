package com.seniorglez.infra.api.v1.controllers;


import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.post;

public class RootController {

    public void init() {
        post("/", (request, response) -> getMain(request, response));
    }

    public String getMain(Request request, Response response) {
        response.status(200);
        response.type("application/json"); //TODO: Fix the json with the new lines
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
    }
}
