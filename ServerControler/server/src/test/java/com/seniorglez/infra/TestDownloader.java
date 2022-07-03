package com.seniorglez.infra;

import org.junit.Test;

import com.seniorglez.infra.fileManagement.Downloader;

public class TestDownloader {

    @Test
    public  void TestSmokeDownload() {
        (new Downloader()).Download("https://launcher.mojang.com/v1/objects/0a269b5f2c5b93b1712d0f5dc43b6182b9ab254e/server.jar","/tmp/server.jar");
    }
}
