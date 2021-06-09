package com.seniorglez.domain;

import com.seniorglez.domain.model.DownloadErrors;
import com.seniorglez.functionalJava.monads.Result;

import java.io.File;

public interface Downloaders {

    public Result<File, DownloadErrors> Download(String url, String targetURL);

}
