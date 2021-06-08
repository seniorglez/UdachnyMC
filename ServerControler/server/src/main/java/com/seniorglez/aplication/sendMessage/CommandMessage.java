package com.seniorglez.aplication.sendMessage;

import java.util.Date;

public class CommandMessage {

    private String command;
    private Date expiration;

    public CommandMessage( String command ) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
