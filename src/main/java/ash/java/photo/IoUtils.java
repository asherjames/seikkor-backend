package ash.java.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Asher on 23/03/2016.
 */
public class IoUtils {

    private static final String PROPERTIES_FILE = "config.properties";

    private static final Logger Log = LoggerFactory.getLogger(IoUtils.class);

    private IoUtils() {}

    public static BufferedImage loadImage(String imageName) {
        Log.debug("Attempting to load " + imageName + "...");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            throw new PhotoWsException("IO Exception while loading image", e);
        }
        return img;
    }

    public static Properties loadProperties() {
        Log.debug("Attempting to load properties...");
        try {
            ClassLoader loader = IoUtils.class.getClassLoader();
            InputStream input = loader.getResourceAsStream(PROPERTIES_FILE);
            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new PhotoWsException("IO Exception while loading properties", e);
        }
    }
}
