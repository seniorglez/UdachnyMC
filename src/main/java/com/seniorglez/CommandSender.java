package com.seniorglez;

import com.seniorglez.util.PropertiesReader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class CommandSender {

    private final Process mcProcess;
    private final OutputStreamWriter mcWriter;
    private final PropertiesReader propertiesReader;

    public CommandSender(Process mcProcess) {
        this.mcProcess = mcProcess;
        OutputStream serverInput = mcProcess.getOutputStream();
        mcWriter = new OutputStreamWriter(serverInput);
        propertiesReader = new PropertiesReader();
    }

    public int sendMessage(String msg) throws IOException {
        String command  = Arrays.stream( msg.split(" ") ).findFirst().get();
        System.out.println( "trying to execute a " + command + " command" );
        String propertyValue = propertiesReader.getProperty( command );
        if(!Boolean.parseBoolean(propertyValue)) return -1;
        mcWriter.write(msg + "\n");
        mcWriter.flush();
        return 0;
    }
}
