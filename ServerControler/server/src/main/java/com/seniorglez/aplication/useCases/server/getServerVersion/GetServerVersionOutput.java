package com.seniorglez.aplication.useCases.server.getServerVersion;

public class GetServerVersionOutput {

    private String version;
    public GetServerVersionOutput(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
