package com.seniorglez.infra;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.seniorglez.domain.Reads;

public class TailReader implements Reads {

    private static final class FileBuffer {
        private int offset = 0;
        private final int lines;
        private final String[] result;

        public FileBuffer(int lines) {
            this.lines = lines;
            this.result = new String[lines];
        }

        public void collect(String line) {
            result[offset++ % lines] = line;
        }

        public List<String> getLines() {
            return IntStream.range((offset < lines) ? 0 : offset - lines, offset)
                    .mapToObj(idx -> result[idx % lines]).collect(Collectors.toList());
        }
    }

    @Override
    public List<String> readLastLines(Path source, int lines) {
        try (Stream<String> stream = Files.lines(source)) {
            FileBuffer fileBuffer = new FileBuffer(lines);
            stream.forEach(line -> fileBuffer.collect(line));
            return fileBuffer.getLines();
        } catch (IOException e) {
            return null;
        }
    }
}
