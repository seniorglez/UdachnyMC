package com.seniorglez.infra;

import static org.junit.Assert.assertTrue;

import com.seniorglez.domain.model.ReadErrors;
import com.seniorglez.functionalJava.monads.Result;

import org.junit.Test;

public class TestFileReaderImpl {
    
    @Test
    public void testReadFile() {
        FileReaderImpl impl = new FileReaderImpl();
        Result<String,ReadErrors> res = impl.read("src/test/resources/foo.txt");
        assertTrue(res instanceof Result.Success<?,?>);
        Result.Success<String, ReadErrors> e = (Result.Success<String, ReadErrors>) res;
        assertTrue(e.getValue().equals("brrrum\nabc\nxd"));
    }

    @Test
    public void testReadWrongFile() {
        FileReaderImpl impl = new FileReaderImpl();
        Result<String,ReadErrors> res = impl.read("src/test/resources/cdfriokrdeoikroklpijrdfiklojp.txt");
        assertTrue(res instanceof Result.Failure<?,?>);
    }
}
