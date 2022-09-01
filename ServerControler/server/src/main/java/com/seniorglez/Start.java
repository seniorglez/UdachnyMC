package com.seniorglez;

import com.seniorglez.aplication.services.ServerService;
import com.seniorglez.aplication.services.impl.ServerServiceImpl;
import com.seniorglez.infra.api.v1.controllers.AuthController;
import com.seniorglez.infra.api.v1.controllers.HelloWorldController;
import com.seniorglez.infra.api.v1.controllers.RootController;
import com.seniorglez.infra.api.v1.controllers.ServerController;
import com.seniorglez.infra.fileManagement.PropertiesReader;

import java.io.*;

import static java.lang.System.exit;


public class Start {

    private static ServerService serverService = ServerServiceImpl.getInstance();

    public static void main(String[] args) throws IOException {

        PropertiesReader propertiesReader = new PropertiesReader("config");
        if(!serverService.startServer()) {
            exit(1);
        }
        //start controllers
        new AuthController().init();
        new RootController().init();
        new HelloWorldController().init();
        new ServerController().init();
    }



}
