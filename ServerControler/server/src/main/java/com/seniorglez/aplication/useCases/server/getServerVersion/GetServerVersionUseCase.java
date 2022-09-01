package com.seniorglez.aplication.useCases.server.getServerVersion;

import com.seniorglez.aplication.services.ServerService;
import com.seniorglez.aplication.services.impl.ServerServiceImpl;

public class GetServerVersionUseCase {

    private ServerService serverService;

    public GetServerVersionUseCase() {
        this(ServerServiceImpl.getInstance());
    }

    public GetServerVersionUseCase(ServerService serverService) {
        this.serverService = serverService;
    }

    public GetServerVersionOutput execute() {
        String version = serverService.getVersion();
        return new GetServerVersionOutput(version);
    }
}
