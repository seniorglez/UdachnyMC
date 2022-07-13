package com.seniorglez.aplication.lifeCicle;

import com.seniorglez.aplication.login.Download;
import com.seniorglez.aplication.login.ScrapServerUrl;
import com.seniorglez.domain.model.DownloadErrors;
import com.seniorglez.domain.model.ScrapingErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.fileManagement.Downloader;
import com.seniorglez.infra.fileManagement.Scraper;

import java.io.File;

public class UpdateServer {

    private String serverLocation;

    public UpdateServer(String serverLocation) {
        this.serverLocation = serverLocation;
    }

    public boolean execute() {
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
    return (result instanceof Result.Success);
    }
}
