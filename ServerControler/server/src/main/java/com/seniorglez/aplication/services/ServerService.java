package com.seniorglez.aplication.services;

public interface ServerService {

    boolean startServer();

    boolean stopServer();

    boolean updateServer();

    boolean updateServer(String targetVersion);

    String getVersion();
}
