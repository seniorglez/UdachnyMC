package com.seniorglez.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.seniorglez.domain.FileReader;
import com.seniorglez.domain.model.ReadErrors;
import com.seniorglez.functionalJava.collections.CollectionsUtils;
import com.seniorglez.functionalJava.monads.Option;
import com.seniorglez.functionalJava.monads.Result;

public class FileReaderImpl implements FileReader {

    @Override
    public Result<String, ReadErrors> read(String path) {
        Option<Path> option = new Option<>(Path.of(path));
        return option.flatMap((p) -> {
            try {
                return Files.readAllLines(p); // can't avoid this
            } catch (IOException e) {
                return null;
            }
        })
                .flatMap(l -> CollectionsUtils.flat(l, (x, y) -> x + "\n" + y))
                .orElse(new Option<String>())// This is ugly, I know
                .flatMap(x -> (Result<String, ReadErrors>) new Result.Success<String, ReadErrors>(x))
                .orElse(new Result.Failure<String, ReadErrors>(null));

    }

}
