package com.seniorglez.aplication.endpoints.post;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import com.seniorglez.aplication.login.ValidateToken;
import com.seniorglez.domain.Reads;
import com.seniorglez.domain.model.EndpointResponse;
import com.seniorglez.infra.LogLineRequest;
import com.seniorglez.infra.TokenManager;
import com.google.gson.Gson;

public class PostLastLogs {

    class JsonResponse {
        private List<String> lines;

        public JsonResponse(List<String> lines) {
            this.lines = lines;
        }
    }
    
    private Reads reads;

    public PostLastLogs(Reads reads) {
        this.reads =  reads;
    }

    public EndpointResponse execute(LogLineRequest logLineRequest) {
        if (new ValidateToken(new TokenManager()).execute(logLineRequest.getToken())) {
            List<String> lines = this.reads.readLastLines(Path.of(new File("/minecraft-server/logs/latest.log").getPath()), logLineRequest.getNumber());
            Gson gson = new Gson();
            return new EndpointResponse(200,"application/json",gson.toJson(new JsonResponse(lines)));
        }
        return new EndpointResponse(403,"application/json","{\n\"result\":\"The token is not valid\"\n}");
    }
}
