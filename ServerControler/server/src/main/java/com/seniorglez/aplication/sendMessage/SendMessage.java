package com.seniorglez.aplication.sendMessage;


import com.seniorglez.domain.Messages;
import com.seniorglez.domain.model.MessageErrors;
import com.seniorglez.domain.model.MessageSuccesses;
import com.seniorglez.functionalJava.monads.Result;

public class SendMessage {

    public Messages messages;

    public SendMessage( Messages messages ) {
        this.messages = messages;
    }

    public Result<MessageSuccesses, MessageErrors> execute(CommandMessage commandMessage) {
        return messages.send( commandMessage.getCommand() );
    }
}
