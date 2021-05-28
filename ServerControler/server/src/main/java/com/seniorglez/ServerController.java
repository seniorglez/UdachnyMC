package com.seniorglez;

import com.google.gson.Gson;
import com.seniorglez.model.CommandRequest;
import com.seniorglez.model.Credentials;
import com.seniorglez.user.UserController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.io.*;
import java.util.Date;
import java.util.stream.Stream;
import static spark.Spark.*;
import com.seniorglez.functionalJava.functors.Option;

/**
 * Hello world!
 */
public class ServerController {
    private static SecretKey secretKey;
    private static ServerController serverController;
    private static Process mcProcess;
    private static Thread mcOutput;
    private static CommandSender commandSender;
    private static File home = new File(System.getProperty("user.home"));
    private Option<String> op = new Option<String>("kk");//testing

    public static void main(String[] args) throws IOException {
        serverController = new ServerController();
        mcProcess = serverController.createMinecraftProcess(home); 
        mcOutput = serverController.printMinecraftProcessOutput(mcProcess);
        mcOutput.start();
        commandSender = new CommandSender(mcProcess);
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Gson gson = new Gson();

        post("/mc", (request, response) -> {
            String json = request.queryParams("commandRequest");
            CommandRequest commandRequest = gson.fromJson(json, CommandRequest.class);
            Jws<Claims> jws = serverController.decodeJWT(commandRequest.getToken());
            if (serverController.validateJWT(jws)) {
                boolean commandSenderResponse = commandSender.sendMessage(commandRequest.getCommand());
                return ( commandSenderResponse ) ? "command executed" : "The command does not exist of has been deactivated";
            }
            response.status(403);
            return "The token is not valid";
        });

        post("/login", (request, response) -> {
            String json = request.queryParams("credentials");
            Credentials credentials = gson.fromJson(json, Credentials.class);
            if (UserController.authenticate(credentials.getUsername(), credentials.getPassword())) {
                response.type("json");
                String jwt = Jwts.builder()
                        .signWith(secretKey)
                        .setSubject(credentials.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                        .compact();
                return jwt;
            }
            return "Sorry, username or password incorrect";
        });
    }

    private Process createMinecraftProcess(File home) throws IOException {
        System.out.println("Starting Server!");
        ProcessBuilder mcProcessBuilder = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui");
        mcProcessBuilder.directory(home);
        Process mcProcess = mcProcessBuilder.start();
        return mcProcess;
    }

    private Thread printMinecraftProcessOutput(Process mcProcess){
        InputStream serverOutput = mcProcess.getInputStream();
        Thread printThread = new Thread(() -> {
            Stream<String> lines = new BufferedReader(new InputStreamReader(serverOutput)).lines();
            lines.forEach(l -> System.out.println(l));
        });
        return printThread;
    }

    private Jws<Claims> decodeJWT(String token) {
        if (token == null || token.isEmpty()) return null;
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        return jws;
    }

    private boolean validateJWT(Jws<Claims> jws) {
        if (jws == null) return false;
        Date expirationDate = jws.getBody().getExpiration();
        return expirationDate.after(new Date());
    }
}