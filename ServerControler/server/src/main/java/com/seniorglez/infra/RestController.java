package com.seniorglez.infra;

import com.google.gson.Gson;
import com.seniorglez.aplication.lifeCicle.RestartApplication;
import com.seniorglez.aplication.lifeCicle.UpdateServer;
import com.seniorglez.aplication.login.GenerateToken;
import com.seniorglez.aplication.login.LoginUser;
import com.seniorglez.aplication.login.QueryUser;
import com.seniorglez.aplication.login.ValidateToken;
import com.seniorglez.aplication.sendMessage.CommandMessage;
import com.seniorglez.aplication.sendMessage.SendMessage;
import com.seniorglez.domain.model.MessageErrors;
import com.seniorglez.domain.model.MessageSuccesses;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

import static spark.Spark.get;
import static spark.Spark.post;

public class RestController {

    private final Process mcProcess;
    private final SendMessage sendMessage;
    private final Gson gson;

    public RestController( Process mcProcess, SendMessage sendMessage ) {
        this.mcProcess = mcProcess;
        this.sendMessage = sendMessage;
        this.gson = new Gson();
    }

    public void start() {

            post("/mc", ( request, response ) -> {
                String json = request.queryParams("commandRequest");
                CommandRequest commandRequest = gson.fromJson(json, CommandRequest.class);
                if (new ValidateToken(new TokenManager()).execute(commandRequest.getToken())) {
                    CommandMessage cm = new CommandMessage(commandRequest.getCommand());
                    Result<MessageSuccesses, MessageErrors> result = sendMessage.execute(cm);
                    if(result instanceof Result.Success) {
                        response.status(200);
                        return "Command executed";
                    }
                    switch ((( Result.Failure< MessageSuccesses, MessageErrors >)result).getError()) {
                        case IOEXCEPTION:
                            response.status(500);
                            return "Internal Server Error";
                        case UNAUTHORIZED:
                            response.status(403);
                            return "The token is not valid";
                    }
                }
                response.status(403);
                return "The token is not valid";
            });

            post("/login", (request, response) -> {
                String json = request.queryParams( "credentials" );
                QueryUser queryUser = gson.fromJson( json, QueryUser.class);
                Result<User, UserErrors> loginResult = new LoginUser( new UserJDBC()).execute(queryUser);
                if ( loginResult instanceof Result.Success ) {
                    response.type("json");
                    return new GenerateToken(new TokenManager()).execute(queryUser);
                } else {
                    switch (( UserErrors )(( Result.Failure )loginResult).getError()) {
                        
                        case SERVER_ERROR:
                            response.status( 500 );
                            return "SERVER ERROR";
                        case TIMEOUT:
                            response.status( 503 );
                            return "TIMEOUT";
                        case NO_RESPONSE:
                            response.status( 503 );
                            return "NO RESPONSE";
                        case PASSWORD_NULL:
                            response.status( 400 );
                            return "PASSWORD NULL";
                        case USERNAME_NULL:
                            response.status( 400 );
                            return "USERNAME NULL";
                        case USER_DOES_NOT_EXIST:
                            response.status( 401 );
                            return "USER DOES NOT EXIST";
                        default:
                            response.status( 0 );
                            return "UNTRACKED ERROR";
                    }
                }
            });

            post("/update", (request, response) -> {
                String token = request.queryParams("token");
                if (new ValidateToken(new TokenManager()).execute(token)) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        new RestartApplication();
                    }).start();
                    new UpdateServer(System.getProperty("user.home")+"/server.jar").execute(); //this is lame
                    response.status( 200 );
                    return "Updating...";
                } else {
                    response.status(403);
                    return "The token is not valid";
                }
            });

        get("/world", ((request, response) -> {
            final String filename ="world";
            response.header("Content-disposition", "attachment; filename=" + filename + ".zip;");
            new Zippo().zipDir("/root/"+ filename,"/tmp/" + filename + ".zip");
            File file = new File("/tmp/" + filename + ".zip");
            OutputStream outputStream = response.raw().getOutputStream();
            outputStream.write(Files.readAllBytes(file.toPath()));
            outputStream.flush();
            return response;
            }));
        }

}