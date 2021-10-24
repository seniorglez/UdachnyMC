package com.seniorglez.infra;

import com.seniorglez.domain.model.ScrapingErrors;
import com.seniorglez.functionalJava.monads.Result;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TestScraper {

    final String regex = "(https:\\/\\/launcher).*?server\\.jar"; //(https:\/\/launcher).*?server\.jar

    @Ignore
    @Test
    public void testShouldReturnSuccess() {
        Result<String, ScrapingErrors> res = new Scraper().scrap("https://www.minecraft.net/es-es/download/server",regex);
        assertTrue(res instanceof Result.Success);
        //System.out.println(((Result.Success<String,ScrapingErrors>) res).getValue());
    }
}
