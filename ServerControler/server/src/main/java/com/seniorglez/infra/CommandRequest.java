package com.seniorglez.infra;

public class CommandRequest {

    private String command;
    private String token;

    public CommandRequest(String command, String token) {
        this.command = command;
        this.token = token;
    }

    public String getCommand() {
        return command;
    }

    public String getToken() {
        return token;
    }

}
