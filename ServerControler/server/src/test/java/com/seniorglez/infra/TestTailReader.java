package com.seniorglez.infra;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

public class TestTailReader {

    Path p = Path.of(new File("src/test/resources/tail.txt").getPath());

    @Test
    public void testReader4Lines() {
        TailReader tr = new TailReader();
        List<String> l = tr.readLastLines(p, 4);
        assertTrue(l.get(0).equals("Jesus Navas"));
        assertTrue(l.get(1).equals("21-Nov-1985"));
        assertTrue(l.get(2).equals("1.72 m"));
        assertTrue(l.get(3).equals("Sevilla FC (ESP)"));
    }

}
