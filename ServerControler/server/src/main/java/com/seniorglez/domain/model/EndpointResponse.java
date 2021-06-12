package com.seniorglez.domain.model;

public class EndpointResponse {

    private int responseCode;

    private String responseBody;

    private String responseType;

    public EndpointResponse(int responseCode,String responseType,String responseBody) {
        this.responseCode = responseCode;
        this.responseType = responseType;
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getResponseType() {
        return responseType;
    }
}
