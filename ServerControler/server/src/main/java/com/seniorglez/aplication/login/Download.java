package com.seniorglez.aplication.login;

import com.seniorglez.domain.Downloaders;
import com.seniorglez.domain.model.DownloadErrors;
import com.seniorglez.functionalJava.monads.Result;

import java.io.File;

public class Download {

    Downloaders downloaders;

    public Download(Downloaders downloaders) {
        this.downloaders = downloaders;
    }

    public Result<File, DownloadErrors> execute(String remoteURL, String targetURL) {
        return downloaders.Download(remoteURL,targetURL);
    }

}
