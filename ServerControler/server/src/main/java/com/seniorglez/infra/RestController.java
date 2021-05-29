package com.seniorglez.infra;

import com.seniorglez.aplication.login.LoginUser;
import com.seniorglez.aplication.login.QueryUser;
import com.seniorglez.aplication.sendMessage.CommandMessage;
import com.seniorglez.aplication.sendMessage.SendMessage;
import com.seniorglez.domain.model.MessageErrors;
import com.seniorglez.domain.model.MessageSuccesses;
import com.seniorglez.domain.model.User;
import com.seniorglez.functionalJava.monads.Option;
import com.seniorglez.functionalJava.monads.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

import static spark.Spark.*;

public class RestController {

    private final Process mcProcess;
    private final SendMessage sendMessage;
    private final Gson gson;
    private SecretKey secretKey;

    public RestController(Process mcProcess, SendMessage sendMessage) {
        this.mcProcess = mcProcess;
        this.sendMessage = sendMessage;
        this.gson = new Gson();
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public void start() {

            post("/mc", (request, response) -> {
                String json = request.queryParams("commandRequest");
                CommandRequest commandRequest = gson.fromJson(json, CommandRequest.class);
                Jws<Claims> jws = decodeJWT(commandRequest.getToken());
                CommandMessage cm = new CommandMessage(commandRequest.getCommand(), jws.getBody().getExpiration());
                Result<MessageSuccesses, MessageErrors> result = sendMessage.execute(cm);
                if(result instanceof Result.Success){
                    return "Command executed";
                }
                switch (((Result.Failure<MessageSuccesses,MessageErrors>)result).getError()){
                    case IOEXCEPTION:
                        response.status(500);
                        return "Internal Server Error";
                    case UNAUTHORIZED:
                        response.status(403);
                        return "The token is not valid";
                }
                response.status(403);
                return "The token is not valid";
            });

            post("/login", (request, response) -> {
                String json = request.queryParams("credentials");
                Credentials credentials = gson.fromJson(json, Credentials.class);
                Option<User> loggedUser = new LoginUser(new UserJDBC()).execute(new QueryUser(credentials.getUsername(),credentials.getPassword()));
                if(loggedUser.getValue() != null){
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