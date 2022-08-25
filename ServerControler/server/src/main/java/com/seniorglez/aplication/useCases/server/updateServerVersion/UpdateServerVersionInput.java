package com.seniorglez.aplication.useCases.server.updateServerVersion;

public class UpdateServerVersionInput {

    private String targetVersion;

    public UpdateServerVersionInput(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public String getTargetVersion() {
        return targetVersion;
    }
}
