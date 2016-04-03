package ash.java.photo;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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

    public static List<ImageInfo> getInfoForAllImages(Properties props) {
        Log.info("Attempting to read filenames from directory...");
        updateImageDirectories(props);
        String fullsizePath = props.getProperty(FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY);
        List<String> filenames = getAllFilenamesInDirectory(fullsizePath);
        List<ImageInfo> info = new ArrayList<>();
        for (String name : filenames) {
            String fullImagePath = createAbsolutePath(fullsizePath, name);
            Dimension imageDim = getImageWidthAndHeight(new File(fullImagePath));
            info.add(new ImageInfo(name, imageDim));
        }
        return info;
    }

    public static void updateImageDirectories(Properties props) {
        Log.info("Updating directories...");
        String fullsizePath = props.getProperty(FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY);
        String thumbnailPath = props.getProperty(THUMBNAIL_IMAGE_FOLDER_PATH_PROPERTY);
        Log.info("Fullsize path is: " + fullsizePath);
        Log.info("Thumbnail path is: " + thumbnailPath);

        int maxThumbWidth = Integer.parseInt(props.getProperty(THUMBNAIL_MAX_WIDTH_PROPERTY));
        int maxThumbHeight = Integer.parseInt(props.getProperty(THUMBNAIL_MAX_HEIGHT_PROPERTY));
        Log.info("Max thumbnail width: " + maxThumbWidth + " , max thumbnail height: " + maxThumbHeight);

        List<String> fullsizeFilenames = getAllFilenamesInDirectory(fullsizePath);
        List<String> thumbnailFilenames = getAllFilenamesInDirectory(thumbnailPath);
        List<String> thumbnailsNeeded = new ArrayList<>(CollectionUtils.subtract(fullsizeFilenames, thumbnailFilenames));
        Log.info("Thumbnails needed: " + thumbnailsNeeded.toString());

        if (thumbnailsNeeded.isEmpty())
            return;
        for(String s : thumbnailsNeeded) {
            BufferedImage img = loadBufferedImage(s, props);
            Log.info("Loaded " + s + " and attempting to scale...");
            BufferedImage scaledImg = ImageUtils.scaleToThumbnail(img, maxThumbWidth, maxThumbHeight);
            File f = new File(createAbsolutePath(thumbnailPath, s));
            try {
                f.createNewFile();
                ImageIO.write(scaledImg, "jpg", f);
            } catch (IOException e) {
                throw new PhotoWsException("Exception thrown while trying to create thumbnail", e);
            }
        }
    }

    private static BufferedImage loadBufferedImage(String imageName, Properties props) {
        Log.info("Attempting to load " + imageName + "...");
        String path = createAbsolutePath(props.getProperty(FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY), imageName);
        Log.debug("Loading " + imageName + " from " + path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new PhotoWsException("IO Exception while loading image", e);
        }
        return img;
    }

    private static Dimension getImageWidthAndHeight(File img) {
        Log.info("Getting image width and height for " + img.getAbsolutePath());
        Dimension dim = null;
        try {
            ImageInputStream in = ImageIO.createImageInputStream(img);
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if(readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    dim =  new Dimension(reader.getWidth(0), reader.getHeight(0));
                } finally {
                    reader.dispose();
                    in.close();
                }
            }
        } catch (IOException e ) {
            throw new PhotoWsException("Exception while trying to get width and height", e);
        }
        return dim;
    }

    private static List<String> getAllFilenamesInDirectory(String path) {
        Log.info("Attempting to get all filenames in directory...");
        File imageFolder = new File(path);
        if (!imageFolder.isDirectory()) {
            throw new PhotoWsException("File specified in config is not a directory!");
        }
        File[] files = imageFolder.listFiles();
        List<String> filenames = new ArrayList<>();
        for (File f : files) {
            String name = f.getName();
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

    private static String createAbsolutePath(String base, String filename) {
        return base + "\\" + filename;
    }
}
