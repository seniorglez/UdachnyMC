package com.seniorglez.infra;

import com.seniorglez.domain.Messages;
import com.seniorglez.domain.model.MessageErrors;
import com.seniorglez.domain.model.MessageSuccesses;
import com.seniorglez.functionalJava.monads.Result;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * A class which allows to send commands to the minecraft server.
 */
public class CommandSender implements Messages {

    private final Process mcProcess;
    private final OutputStreamWriter mcWriter;
    private final PropertiesReader propertiesReader;

    public CommandSender( Process mcProcess ) {
        this.mcProcess = mcProcess;
        OutputStream serverInput = mcProcess.getOutputStream();
        mcWriter = new OutputStreamWriter(serverInput);
        propertiesReader = new PropertiesReader( "commands" );
    }

    @Override
    public Result<MessageSuccesses, MessageErrors> send(String message) {
        return Result.get(()-> {
            try {
                String command  = Arrays.stream( message.split(" ") ).findFirst().get();
                System.out.println( "trying to execute a " + command + " command" );
                String propertyValue = propertiesReader.getProperty( command );
                if(!Boolean.parseBoolean(propertyValue)) return new Result.Failure<MessageSuccesses,MessageErrors>( MessageErrors.UNAUTHORIZED );
                mcWriter.write( message + "\n" );
                mcWriter.flush();
                return new Result.Success< MessageSuccesses, MessageErrors >( MessageSuccesses.SUCCESS );
            } catch ( IOException e ) {
                return new Result.Failure< MessageSuccesses,MessageErrors >( MessageErrors.IOEXCEPTION );
            }
        });
    }
}
