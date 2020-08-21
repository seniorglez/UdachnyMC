package com.seniorglez;

import com.seniorglez.user.User;
import com.seniorglez.user.UserDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.Date;
import java.util.stream.Stream;
import  static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class ServerController
{
    private final String KEY = "keyhahha";
    private static SecretKey secretKey;

    public static void main( String[] args ) throws IOException {
        ServerController serverController = new ServerController();
        Process mcProcess = serverController.CreateMinecraftProcess();
        CommandSender commandSender = new CommandSender(mcProcess);
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(secretKey.getEncoded());
        //System.out.println("key: " + secretString); // just for debugging proposes


        get("/mc", (request, response) -> {
            int responseCode = commandSender.sendMessage(request.queryParams("msg"));
            return (responseCode >= 0)?"command executed":"The command does not exist of has been deactivated";
        } );

        post("/login", (request, response) -> {
            String name = request.queryParams("name");
            String password = request.queryParams("password");
            User current = UserDAO.getInstance().getUserByUsername(name);
            if(current != null) {
                if(current.getPassword().equals(password)) {
                    response.type("json");
                    String jwt = Jwts.builder()
                            .signWith(secretKey)
                            .setSubject(name)
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis()+604800000))
                            .compact();
                    return jwt;
                }
            }
            return "Sorry username or password incorrect";
        });
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

    private String decodeJWT(String token) {
        Jws<Claims> jws;
            jws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        return "";
    }
}
