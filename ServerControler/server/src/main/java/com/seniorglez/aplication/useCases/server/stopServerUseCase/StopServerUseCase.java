package com.seniorglez.aplication.useCases.server.stopServerUseCase;

import com.seniorglez.aplication.services.ServerService;
import com.seniorglez.aplication.services.impl.ServerServiceImpl;

public class StopServerUseCase {
    private ServerService serverService;

    public StopServerUseCase() {
        this(ServerServiceImpl.getInstance());
    }

    public StopServerUseCase(ServerService serverService) {
        this.serverService = serverService;
    }

    public StopServerOutput execute() {
        return new StopServerOutput(serverService.stopServer());
    }

}
