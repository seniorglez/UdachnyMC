package com.seniorglez;

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

/**
 * Hello world!
 */
public class ServerController {
    private static SecretKey secretKey;

    public static void main(String[] args) throws IOException {
        ServerController serverController = new ServerController();
        Process mcProcess = serverController.CreateMinecraftProcess();
        CommandSender commandSender = new CommandSender(mcProcess);
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        post("/mc", (request, response) -> {
            Jws<Claims> jws = serverController.decodeJWT(request.queryParams("token"));
            if (serverController.validateJWT(jws)) {
                int responseCode = commandSender.sendMessage(request.queryParams("msg"));
                return (responseCode >= 0) ? "command executed" : "The command does not exist of has been deactivated";
            }
            return "The token is not valid";
        });

        post("/login", (request, response) -> {
            String name = request.queryParams("name");
            String password = request.queryParams("password");
            if (UserController.authenticate(name, password)) {
                response.type("json");
                String jwt = Jwts.builder()
                        .signWith(secretKey)
                        .setSubject(name)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                        .compact();
                return jwt;
            }
            return "Sorry username or password incorrect";
        });
    }

    private Process CreateMinecraftProcess() throws IOException {
        System.out.println("Starting Server!");
        ProcessBuilder mcProcessBuilder = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui");
        mcProcessBuilder.directory(new File("/Users/diego/Developer/Playground/minecraftserver"));
        Process mcProcess = mcProcessBuilder.start();
        InputStream serverOutput = mcProcess.getInputStream();
        new Thread(() -> {
            Stream<String> lines = new BufferedReader(new InputStreamReader(serverOutput)).lines();
            lines.forEach(l -> System.out.println(l));
        }).start();
        return mcProcess;
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