package com.seniorglez.aplication.useCases.server.updateServerVersion;

import com.seniorglez.aplication.services.ServerService;
import com.seniorglez.aplication.services.impl.ServerServiceImpl;

import static java.util.Objects.isNull;

public class UpdateServerVersionUseCase {

    private ServerService serverService;

    public UpdateServerVersionUseCase() {
        this(ServerServiceImpl.getInstance());
    }

    public UpdateServerVersionUseCase(ServerService serverService) {
        this.serverService = serverService;
    }

    public UpdateServerVersionOutput execute(UpdateServerVersionInput input) {
        if(isNull(input.getTargetVersion())) {
            return new UpdateServerVersionOutput(serverService.updateServer());
        }
        return new UpdateServerVersionOutput(serverService.updateServer(input.getTargetVersion()));
    }
}
