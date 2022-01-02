package com.seniorglez.domain;

import java.nio.file.Path;
import java.util.List;

public interface Reads {

    public abstract List<String> readLastLines(Path source, int lines);
    
}
