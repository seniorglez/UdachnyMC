package com.seniorglez.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * This class allows to access the configuration of the application stored on the commands.properties file located under
 * the resources directory.
 */
public class PropertiesReader {

    private Properties properties;

    /**
     * Construct a new {@link PropertiesReader}
     */
    public PropertiesReader() {
        properties = new Properties();
        InputStream inputStream = PropertiesReader.class.getResourceAsStream("/commands.properties");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        try {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key associated with one value.
     * @return the value associated.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}