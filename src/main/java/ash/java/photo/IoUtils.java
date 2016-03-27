package ash.java.photo;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Asher on 23/03/2016.
 */
public class IoUtils {

    private static final String PROPERTIES_FILE = "config.properties";
    private static final String FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY = "fullsizeImageFolderPath";
    private static final String THUMBNAIL_IMAGE_FOLDER_PATH_PROPERTY = "thumbnailImageFolderPath";
    private static final String THUMBNAIL_MAX_WIDTH_PROPERTY = "maxThumbnailWidth";
    private static final String THUMBNAIL_MAX_HEIGHT_PROPERTY = "maxThumbnailHeight";

    private static final Logger Log = LoggerFactory.getLogger(IoUtils.class);

    private IoUtils() {}

    public static BufferedImage loadImage(String imageName, ImageTypeEnum type) {
        Log.info("Attempting to load " + imageName + "...");
        Properties props = loadProperties();
        String path = type == ImageTypeEnum.FULLSIZE ?
                props.getProperty(FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY) + imageName :
                props.getProperty(THUMBNAIL_IMAGE_FOLDER_PATH_PROPERTY) + imageName;
        Log.debug("Loading " + imageName + " from " + path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new PhotoWsException("IO Exception while loading image", e);
        }
        return img;
    }

    public static List<ImageInfo> getInfoForAllImages() {
        Log.info("Attempting to read filenames from directory");
        Properties props = loadProperties();
        updateImageDirectories(props);
        String path = props.getProperty(FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY);
        List<String> filenames = getAllFilenamesInDirectory(path);
        List<ImageInfo> info = new ArrayList<>();
        for (String name : filenames) {
            BufferedImage img = loadImage(name, ImageTypeEnum.FULLSIZE);
            info.add(ImageUtils.getInfo(img, name));
        }
        return info;
    }

    public static void updateImageDirectories(Properties props) {
        Log.info("Updating directories");
        String fullsizePath = props.getProperty(FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY);
        String thumbnailPath = props.getProperty(THUMBNAIL_IMAGE_FOLDER_PATH_PROPERTY);

        int maxThumbWidth = Integer.parseInt(props.getProperty(THUMBNAIL_MAX_WIDTH_PROPERTY));
        int maxThumbHeight = Integer.parseInt(props.getProperty(THUMBNAIL_MAX_HEIGHT_PROPERTY));

        List<String> fullsizeFilenames = getAllFilenamesInDirectory(fullsizePath);
        List<String> thumbnailFilenames = getAllFilenamesInDirectory(thumbnailPath);
        List<String> thumbnailsNeeded = new ArrayList<>(CollectionUtils.subtract(fullsizeFilenames, thumbnailFilenames));

        if (thumbnailsNeeded.isEmpty())
            return;
        for(String s : thumbnailsNeeded) {
            BufferedImage img = loadImage(s, ImageTypeEnum.FULLSIZE);
            ImageUtils.scaleToThumbnail(img, maxThumbWidth, maxThumbHeight);
            File f = new File(thumbnailPath + s);
            try {
                f.createNewFile();
                ImageIO.write(img, "jpg", f);
            } catch (IOException e) {
                throw new PhotoWsException("Exception thrown while trying to create thumbnail", e);
            }
        }
    }

    private static List<String> getAllFilenamesInDirectory(String path) {
        File imageFolder = new File(path);
        if (!imageFolder.isDirectory()) {
            throw new PhotoWsException("File specified in config is not a directory!");
        }
        File[] files = imageFolder.listFiles();
        List<String> filenames = new ArrayList<>();
        for (File f : files) {
            String name = FilenameUtils.getBaseName(f.getName());
            filenames.add(name);
        }
        return filenames;
    }

    public static Properties loadProperties() {
        Log.info("Attempting to load properties...");
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
