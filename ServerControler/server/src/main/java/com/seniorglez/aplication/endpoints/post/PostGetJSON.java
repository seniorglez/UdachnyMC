package com.seniorglez.aplication.endpoints.post;

import com.seniorglez.domain.model.EndpointResponse;

public class PostGetJSON {

    public EndpointResponse execute(String jsonName) { //WIP
        switch (jsonName) {
            case "banned-ips":
            case "banned-players":
            case "ops":
            case "usercache":
            case "whitelist":
            return new EndpointResponse(200, "JSON", null);
            default:
            return new EndpointResponse(400, "JSON", "{}");

        }
        
    }
    
}
