package com.seniorglez.aplication.useCases.getJSON;

import com.seniorglez.domain.FileReader;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.domain.model.ReadErrors;
import com.seniorglez.functionalJava.monads.Result;

public class GetJSONUseCase {

    private FileReader fileReader;

    public GetJSONUseCase( FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public EndpointResponse execute(String token, String jsonName) { //WIP
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
