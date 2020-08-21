package com.seniorglez;

import com.seniorglez.util.PropertiesReader;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestPropertiesReader {
    @Test
    public void testGetProperties() {
        PropertiesReader p = new PropertiesReader();
        String property = p.getProperty("ability");
        assertTrue(property.equals("true") || property.equals("false"));
    }
}
