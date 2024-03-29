package com.seniorglez.infra.api.v1.requests.json;

public class JSONRequest {

    private String fileName;
    private String token;

    public JSONRequest(String fileName, String token) {
        this.fileName = fileName;
        this.token = token;
    }

    public String getFileName() {
        return fileName;
    }

    public String getToken() {
        return token;
    }

}
