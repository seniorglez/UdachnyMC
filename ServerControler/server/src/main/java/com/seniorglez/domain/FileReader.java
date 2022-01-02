package com.seniorglez.domain;

import com.seniorglez.domain.model.ReadErrors;
import com.seniorglez.functionalJava.monads.Result;

public interface FileReader {

    public Result<String,ReadErrors> read(String path);
    
}
