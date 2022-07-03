package com.seniorglez.aplication.useCases;

import com.seniorglez.domain.Zippable;
import com.seniorglez.infra.api.v1.response.EndpointResponse;

public class GetLogs {

    private Zippable zip;

    public GetLogs(Zippable zip) {
        this.zip = zip;
    }

    public EndpointResponse execute (String token, String urlIn, String urlOut) {
        if(zip.zipDir(urlIn,urlOut)) {
            return new EndpointResponse(200, null, null);
        } 
        return new EndpointResponse(401, "json", "predicadotusmuertos");
    }
}
