package com.seniorglez.aplication.useCases;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import com.seniorglez.domain.Reads;
import com.seniorglez.infra.api.v1.response.EndpointResponse;
import com.seniorglez.infra.api.v1.requests.log.LogLineRequest;
import com.google.gson.Gson;

public class GetLastLogs {

    class JsonResponse {
        private List<String> lines;

        public JsonResponse(List<String> lines) {
            this.lines = lines;
        }
    }
    
    private Reads reads;

    public GetLastLogs(Reads reads) {
        this.reads =  reads;
    }

    public EndpointResponse execute(LogLineRequest logLineRequest) {
            List<String> lines = this.reads.readLastLines(Path.of(new File("/minecraft-server/logs/latest.log").getPath()), logLineRequest.getNumber());
            Gson gson = new Gson();
            return new EndpointResponse(200,"application/json",gson.toJson(new JsonResponse(lines)));
    }
}
