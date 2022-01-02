package com.seniorglez.aplication.endpoints.post;

import com.seniorglez.aplication.login.ValidateToken;
import com.seniorglez.domain.FileReader;
import com.seniorglez.domain.model.EndpointResponse;
import com.seniorglez.domain.model.ReadErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.TokenManager;

public class PostGetJSON {

    private FileReader fileReader;
    private ValidateToken validateToken;

    public PostGetJSON( FileReader fileReader) {
        this.fileReader = fileReader;
        this.validateToken = new ValidateToken(new TokenManager());
    }

    public EndpointResponse execute(String token, String jsonName) { //WIP
        if(!validateToken.execute(token)) return new EndpointResponse(401, "JSON", "{}");
        switch (jsonName) {
            case "banned-ips":
            case "banned-players":
            case "ops":
            case "usercache":
            case "whitelist":
            Result<String, ReadErrors> res = fileReader.read(System.getProperty("user.home") + "/minecraft-server/" + jsonName + ".json");
            if(res instanceof Result.Success<?,?>) {
                String value = ((Result.Success<String,ReadErrors>) res).getValue();
                return new EndpointResponse(200, "JSON", value);
            } else {
                return new EndpointResponse(500, "JSON", "{}");
            }
            
            default:
            return new EndpointResponse(404, "JSON", "{}");

        }
        
    }
    
}
