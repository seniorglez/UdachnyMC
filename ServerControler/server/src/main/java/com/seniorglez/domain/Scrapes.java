package com.seniorglez.domain;

import com.seniorglez.domain.model.ScrapingErrors;
import com.seniorglez.functionalJava.monads.Result;


public interface Scrapes {

    public Result<String, ScrapingErrors> scrap(String url, String re);
}
