package com.seniorglez.aplication.login;

import com.seniorglez.domain.Scrapes;
import com.seniorglez.domain.model.ScrapingErrors;
import com.seniorglez.functionalJava.monads.Result;

public class ScrapServerUrl {

    private final Scrapes scrapes;

    final String regex = "(https:\\/\\/launcher).*?server\\.jar";
    final String url = "https://www.minecraft.net/es-es/download/server";

    public ScrapServerUrl(Scrapes scrapes) {
        this.scrapes = scrapes;
    }

    public Result<String, ScrapingErrors> execute() {
        return scrapes.scrap(url,regex);
    }
}
