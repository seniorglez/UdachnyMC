package com.seniorglez.aplication.lifeCicle;

import com.seniorglez.aplication.login.Download;
import com.seniorglez.aplication.login.ScrapServerUrl;
import com.seniorglez.domain.model.DownloadErrors;
import com.seniorglez.domain.model.ScrapingErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.Downloader;
import com.seniorglez.infra.Scraper;

import java.io.File;

public class UpdateServer {

    private String serverLocation;

    public UpdateServer(String serverLocation) {
        this.serverLocation = serverLocation;
    }

    public boolean execute() {
        Result<String, ScrapingErrors> res = new ScrapServerUrl(new Scraper()).execute();
        if(res instanceof Result.Success) {
            String url = ((Result.Success<String, ScrapingErrors>) res).getValue();
            Result<File, DownloadErrors> result = new Download(new Downloader()).execute(url,serverLocation);
            return (result instanceof Result.Success);
        }
            return false;
    }
}
