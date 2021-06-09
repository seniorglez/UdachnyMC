package com.seniorglez.infra;

import com.seniorglez.domain.Downloaders;
import com.seniorglez.domain.model.DownloadErrors;
import com.seniorglez.functionalJava.monads.Result;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Downloader implements Downloaders {
    @Override
    public Result<File, DownloadErrors> Download(String remoteURL, String targetURL) {
        return  Result.get(()-> {
            File target = new File( targetURL );
            try {
                URL remote = new URL( remoteURL );
                try ( InputStream in = remote.openStream() ) {
                    Files.copy(in, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    return new Result.Success<File, DownloadErrors>(target);
                } catch (IOException e) {
                    return new Result.Failure<File,DownloadErrors>(DownloadErrors.IOERROR);
                }
            } catch ( MalformedURLException e ) {
                return new Result.Failure<File,DownloadErrors>(DownloadErrors.MALFORMED);
            }
        });

    }

}
