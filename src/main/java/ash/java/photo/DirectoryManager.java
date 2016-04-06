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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Asher on 23/03/2016.
 */
public class DirectoryManager {

    private static final Logger Log = LoggerFactory.getLogger(DirectoryManager.class);

    private DirectoryManager() {}

    public static List<ImageInfo> getInfoForAllImages(PropertiesWrapper props) {
        Log.info("Attempting to read filenames from directory...");
        updateImageDirectories(props);
        String fullsizePath = props.getFullsizePath();
        List<String> filenames = getAllFilenamesInDirectory(fullsizePath);
        List<ImageInfo> info = new ArrayList<>();
        for (String name : filenames) {
            String fullImagePath = createAbsolutePath(fullsizePath, name);
            Dimension imageDim = getImageWidthAndHeight(new File(fullImagePath));
            info.add(new ImageInfo(name, imageDim));
        }
        return info;
    }

    public static void updateImageDirectories(PropertiesWrapper props) {
        Log.info("Updating directories...");

        String fullsizePath = props.getFullsizePath();
        String thumbnailPath = props.getThumbnailPath();
        Log.info("Fullsize path is: " + fullsizePath + "\nThumbnail path is: " + thumbnailPath);

        int maxThumbWidth = props.getThumbnailWidth();
        int maxThumbHeight = props.getThumbnailHeight();
        Log.info("Max thumbnail width: " + maxThumbWidth + " , max thumbnail height: " + maxThumbHeight);

        List<String> fullsizeFilenames = getAllFilenamesInDirectory(fullsizePath);
        List<String> thumbnailFilenames = getAllFilenamesInDirectory(thumbnailPath);
        List<String> thumbnailsNeeded = new ArrayList<>(CollectionUtils.subtract(fullsizeFilenames, thumbnailFilenames));
        Log.info("Thumbnails needed: " + thumbnailsNeeded.toString());

        if (!thumbnailsNeeded.isEmpty())
            createAndSaveThumbnails(thumbnailsNeeded, props);
    }

    private static void createAndSaveThumbnails(List<String> thumbnailsNeeded, PropertiesWrapper props) {
        if (thumbnailsNeeded.isEmpty())
            return;
        for(String s : thumbnailsNeeded) {
            BufferedImage img = loadBufferedImage(s, props);
            Log.info("Loaded " + s + " and attempting to scale...");
            BufferedImage scaledImg = ImageUtils.scaleToThumbnail(img, props.getThumbnailWidth(),
                    props.getThumbnailHeight());
            File f = new File(createAbsolutePath(props.getThumbnailPath(), s));
            try {
                f.createNewFile();
                ImageIO.write(scaledImg, "jpg", f);
            } catch (IOException e) {
                throw new PhotoWsException("Exception thrown while trying to create thumbnail", e);
            }
        }
    }

    private static BufferedImage loadBufferedImage(String imageName, PropertiesWrapper props) {
        Log.info("Attempting to load " + imageName + "...");
        String path = createAbsolutePath(props.getFullsizePath(), imageName);
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

    private static String createAbsolutePath(String base, String filename) {
        return base + "\\" + filename;
    }
}
