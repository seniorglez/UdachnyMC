package com.seniorglez.aplication.useCases;


import com.seniorglez.aplication.sendMessage.CommandMessage;
import com.seniorglez.aplication.sendMessage.SendMessage;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.domain.model.MessageErrors;
import com.seniorglez.domain.model.MessageSuccesses;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.api.v1.requests.command.CommandRequest;
import com.seniorglez.infra.server.CommandSender;

public class PostMinecraftCommand {

    private final Process mcProcess;

    public PostMinecraftCommand(Process mcProcess) {
        this.mcProcess = mcProcess;
    }

    public EndpointResponse execute(CommandRequest commandRequest) {
            CommandMessage cm = new CommandMessage(commandRequest.getCommand());
            
            Result<MessageSuccesses, MessageErrors> result = new SendMessage(new CommandSender(this.mcProcess)).execute(cm);
            if(result instanceof Result.Success) {
                return new EndpointResponse(200,"application/json","{\n\"result\":\"Command executed\"\n}");
            }
            switch ((( Result.Failure< MessageSuccesses, MessageErrors >)result).getError()) {
                case IOEXCEPTION:
                    return new EndpointResponse(500,"application/json","{\n\"result\":\"Internal Server Error\"\n}");
                case UNAUTHORIZED:
                    return new EndpointResponse(403,"application/json","{\n\"result\":\"The token is not valid\"\n}");
            }
            return null; //refactor
        }
    }

