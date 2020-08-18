package com.seniorglez;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CommandSender {

    private final Process mcProcess;
    private final OutputStreamWriter mcWriter;

    public CommandSender(Process mcProcess) throws IOException {
        this.mcProcess = mcProcess;
        OutputStream serverInput = mcProcess.getOutputStream();
        mcWriter = new OutputStreamWriter(serverInput);
    }

    public void sendMessage(String msg) throws IOException {
        mcWriter.write(msg + "\n");
        mcWriter.flush();
    }
}
