package com.seniorglez;

import com.seniorglez.aplication.lifeCicle.UpdateServer;
import com.seniorglez.infra.api.v1.controllers.AuthController;
import com.seniorglez.infra.api.v1.controllers.HelloWorldController;
import com.seniorglez.infra.api.v1.controllers.RootController;
import com.seniorglez.infra.fileManagement.PropertiesReader;

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
        getPrintMinecraftProcessOutputThead(mcProcess);
        PropertiesReader propertiesReader = new PropertiesReader("config");
        //start controllers
        new AuthController().init();
        new RootController().init();
        new HelloWorldController().init();
    }

    private static Process createMinecraftProcess(File home) throws IOException {
        System.out.println("Starting Server!");
        ProcessBuilder mcProcessBuilder = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui");
        mcProcessBuilder.directory(home);
        Process mcProcess = mcProcessBuilder.start();
        return mcProcess;
    }

    private static Thread getPrintMinecraftProcessOutputThead(Process mcProcess) {
        InputStream serverOutput = mcProcess.getInputStream();
        Thread printThread = new Thread(() -> {
            Stream<String> lines = new BufferedReader(new InputStreamReader(serverOutput)).lines();
            lines.forEach(l -> System.out.println(l));
        });
        return printThread;
    }

}
