package ash.java.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by AsherJ on 6/04/2016.
 */
public class PropertiesLoader {

    private static final String PROPERTIES_FILENAME = "config.properties";

    public static final Logger Log = LoggerFactory.getLogger(PropertiesLoader.class);

    public static Properties loadProperties() {
        Log.info("Attempting to load properties...");
        try {
            ClassLoader loader = IoUtils.class.getClassLoader();
            InputStream input = loader.getResourceAsStream(PROPERTIES_FILENAME);
            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new PhotoWsException("IO Exception while loading properties", e);
        }
    }
}
