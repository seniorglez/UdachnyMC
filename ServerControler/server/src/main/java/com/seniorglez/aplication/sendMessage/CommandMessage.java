package com.seniorglez.aplication.sendMessage;

import java.util.Date;

public class CommandMessage {

    private String command;
    private Date expiration;

    public CommandMessage(String command, Date expiration) {
        this.command = command;
        this.expiration = expiration;
    }

    public String getCommand() {
        return command;
    }

    public Date getExpiration() {
        return expiration;
    }
}
