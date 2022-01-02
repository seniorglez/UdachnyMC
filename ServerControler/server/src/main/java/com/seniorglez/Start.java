package com.seniorglez;

import com.seniorglez.aplication.lifeCicle.UpdateServer;
import com.seniorglez.aplication.sendMessage.SendMessage;
import com.seniorglez.infra.CommandSender;
import com.seniorglez.infra.RestController;
import java.io.*;
import java.util.stream.Stream;

public class Start {

    public static void main(String[] args) throws IOException {
        String home = System.getProperty("user.home");
        if (!new File(home + "/minecraft-server/server.jar").exists()) {
            if (!new UpdateServer(home + "/minecraft-server/server.jar").execute())
                return;
        }
        Process mcProcess = createMinecraftProcess(new File(home + "/minecraft-server"));
        printMinecraftProcessOutput(mcProcess);
        new RestController(mcProcess, new SendMessage(new CommandSender(mcProcess))).start();
    }

    private static Process createMinecraftProcess(File home) throws IOException {
        System.out.println("Starting Server!");
        ProcessBuilder mcProcessBuilder = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui");
        mcProcessBuilder.directory(home);
        Process mcProcess = mcProcessBuilder.start();
        return mcProcess;
    }

    private static Thread printMinecraftProcessOutput(Process mcProcess) {
        InputStream serverOutput = mcProcess.getInputStream();
        Thread printThread = new Thread(() -> {
            Stream<String> lines = new BufferedReader(new InputStreamReader(serverOutput)).lines();
            lines.forEach(l -> System.out.println(l));
        });
        return printThread;
    }

}
