package com.seniorglez.aplication.useCases.server.startServer;

import com.seniorglez.aplication.services.ServerService;
import com.seniorglez.aplication.services.impl.ServerServiceImpl;

public class StartServerUseCase {

    private ServerService serverService;

    public StartServerUseCase() {
        this(ServerServiceImpl.getInstance());
    }

    public StartServerUseCase(ServerService serverService) {
        this.serverService = serverService;
    }

    public StartServerOutput execute() {
        return new StartServerOutput(serverService.startServer());
    }
}
