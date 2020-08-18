package com.seniorglez;

import java.io.*;
import java.util.stream.Stream;
import  static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        App app = new App();
        Process mcProcess = app.CreateMinecraftProcess();

        CommandSender commandSender = new CommandSender(mcProcess);
        get("/mc", (request, response) ->{
            System.out.println(request.queryParams("msg"));
            commandSender.sendMessage(request.queryParams("msg"));
            return "thanks";
        } );

        
    }



    private Process CreateMinecraftProcess() throws IOException {
        System.out.println( "Starting Server!" );
        ProcessBuilder mcProcessBuilder = new ProcessBuilder("java", "-Xmx1024M","-Xms1024M", "-jar", "server.jar", "nogui");
        mcProcessBuilder.directory(new File("/Users/diego/Developer/Playground/minecraftserver"));
        Process mcProcess = mcProcessBuilder.start();
        InputStream serverOutput = mcProcess.getInputStream();
        new Thread(() -> {
            Stream<String> lines = new BufferedReader(new InputStreamReader(serverOutput)).lines();
            lines.forEach(l -> System.out.println(l));
        }).start();
        return mcProcess;
    }
}
