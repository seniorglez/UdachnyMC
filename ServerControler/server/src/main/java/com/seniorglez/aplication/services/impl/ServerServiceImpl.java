package com.seniorglez.aplication.services.impl;

import com.seniorglez.aplication.login.Download;
import com.seniorglez.aplication.services.ServerService;
import com.seniorglez.domain.model.DownloadErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.api.v1.auth.TokenManagerImpl;
import com.seniorglez.infra.fileManagement.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.stream.Stream;

public class ServerServiceImpl implements ServerService {

    private Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);

    private Process mcProcess;

    private String serverLocation = System.getProperty("user.home") + "/minecraft-server/server.jar";

    private String serverDirectory = System.getProperty("user.home") + "/minecraft-server";

    private static class LazyHolder {
        static final ServerServiceImpl INSTANCE = new ServerServiceImpl();
    }

    private ServerServiceImpl(){}

    public static ServerServiceImpl getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public boolean startServer() {
        try {
            if (!new File(serverLocation).exists()) {
                updateServer();
            }
            this.mcProcess = createMinecraftProcess(new File(serverDirectory));
            getPrintMinecraftProcessOutputThead(mcProcess);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void stopServer() {
        mcProcess.destroy();
    }

    @Override
    public void updateServer() {
        /*
        Result<String, ScrapingErrors> res = new ScrapServerUrl(new Scraper()).execute();
        if(res instanceof Result.Success) {
            String url = ((Result.Success<String, ScrapingErrors>) res).getValue();
            Result<File, DownloadErrors> result = new Download(new Downloader()).execute(url,serverLocation);
            return (result instanceof Result.Success);
        }
            return false;
        }
        */
        String url = "https://launcher.mojang.com/v1/objects/a16d67e5807f57fc4e550299cf20226194497dc2/server.jar";
        Result<File, DownloadErrors> result = new Download(new Downloader()).execute(url,serverLocation);
    }

    private static Process createMinecraftProcess(File home) throws IOException {
        System.out.println("Starting Server!");
        ProcessBuilder mcProcessBuilder = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui");
        mcProcessBuilder.directory(home);
        Process mcProcess = mcProcessBuilder.start();
        return mcProcess;
    }

    private static Thread getPrintMinecraftProcessOutputThead(Process mcProcess) {
        InputStream serverOutput = mcProcess.getInputStream();
        Thread printThread = new Thread(() -> {
            Stream<String> lines = new BufferedReader(new InputStreamReader(serverOutput)).lines();
            lines.forEach(l -> System.out.println(l));
        });
        return printThread;
    }

}
