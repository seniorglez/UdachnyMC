package com.seniorglez.infra.fileManagement;

import com.seniorglez.domain.Scrapes;
import com.seniorglez.domain.model.ScrapingErrors;
import com.seniorglez.functionalJava.monads.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Scraper implements Scrapes {

    @Override
    public Result<String, ScrapingErrors> scrap(String url, String re) {
        return Result.get(()-> {
            try {
                URL remote = new URL(url);
                try(BufferedReader in = new BufferedReader(new InputStreamReader(remote.openStream()))) {
                    String html = in.lines().map(Object::toString).collect(Collectors.joining());
                    Pattern pattern = Pattern.compile(re, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(html);
                    String result = null;
                    if(matcher.find()) result = matcher.group(0);
                    if(result == null) return new Result.Failure<String, ScrapingErrors>(ScrapingErrors.NULL_RESULT);
                    return (!result.isEmpty())? new Result.Success<String, ScrapingErrors>(result): new Result.Failure<String, ScrapingErrors>(ScrapingErrors.NULL_RESULT);
                }
            } catch (MalformedURLException e) {
                return new Result.Failure<String, ScrapingErrors>(ScrapingErrors.MALFORMED);
            } catch (IOException e) {
              return new Result.Failure<String, ScrapingErrors>(ScrapingErrors.IOERROR);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return new Result.Failure<String, ScrapingErrors>(ScrapingErrors.NO_MATCH_FOUND);
            }
        });

    }
}
