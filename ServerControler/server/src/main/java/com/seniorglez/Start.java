package com.seniorglez;

import com.seniorglez.aplication.login.Download;
import com.seniorglez.aplication.login.ScrapServerUrl;
import com.seniorglez.aplication.sendMessage.SendMessage;
import com.seniorglez.domain.model.DownloadErrors;
import com.seniorglez.domain.model.ScrapingErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.CommandSender;
import com.seniorglez.infra.Downloader;
import com.seniorglez.infra.RestController;
import com.seniorglez.infra.Scraper;


import java.io.*;
import java.util.stream.Stream;


public class Start {

    public static void main(String[] args) throws IOException {
        String home = System.getProperty("user.home");
        if(!new File(home+"/server.jar").exists()) {
            Result<String, ScrapingErrors> res = new ScrapServerUrl(new Scraper()).execute();
            if(res instanceof Result.Success) {
                String url = ((Result.Success<String, ScrapingErrors>) res).getValue();
                Result<File, DownloadErrors> result = new Download(new Downloader()).execute(url,home+"/server.jar");
                if (result instanceof Result.Failure) return;
            } else {
                return;
            }
        }
        Process mcProcess = createMinecraftProcess(new File(home));
        printMinecraftProcessOutput(mcProcess);
        new RestController(mcProcess, new SendMessage(new CommandSender(mcProcess))).start();
    }

    private static Process createMinecraftProcess(File home) throws IOException {
        System.out.println("Starting Server!");
        ProcessBuilder mcProcessBuilder = new ProcessBuilder("java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui");
        mcProcessBuilder.directory(home);
        Process mcProcess = mcProcessBuilder.start();
        return mcProcess;
    }

    private static Thread printMinecraftProcessOutput(Process mcProcess) {
        InputStream serverOutput = mcProcess.getInputStream();
        Thread printThread = new Thread(() -> {
            Stream<String> lines = new BufferedReader(new InputStreamReader(serverOutput)).lines();
            lines.forEach(l -> System.out.println(l));
        });
        return printThread;
    }

}
