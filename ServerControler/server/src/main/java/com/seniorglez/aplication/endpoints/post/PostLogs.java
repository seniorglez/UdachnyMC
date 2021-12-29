package com.seniorglez.aplication.endpoints.post;

import com.seniorglez.aplication.login.ValidateToken;
import com.seniorglez.domain.Zippable;
import com.seniorglez.domain.model.EndpointResponse;
import com.seniorglez.infra.TokenManager;

public class PostLogs {

    private ValidateToken validateToken;
    private Zippable zip;

    public PostLogs(Zippable zip) {
        this.zip = zip;
        this.validateToken = new ValidateToken(new TokenManager());
    }

    public EndpointResponse execute (String token, String urlIn, String urlOut) {
        
        if(!validateToken.execute(token)){
            return new EndpointResponse(401, "json", "predicadotusmuertos");
        }
        
        if(zip.zipDir(urlIn,urlOut)) {
            return new EndpointResponse(200, null, null);
        } 
        return new EndpointResponse(401, "json", "predicadotusmuertos");
    }
}
