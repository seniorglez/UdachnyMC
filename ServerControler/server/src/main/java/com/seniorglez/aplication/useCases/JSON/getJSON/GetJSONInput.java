package com.seniorglez.aplication.useCases.JSON.getJSON;

public class GetJSONInput {

    private String jsonName;

    public GetJSONInput(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonName() {
        return jsonName;
    }

}