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

    private final Logger Log = LoggerFactory.getLogger(DirectoryManager.class);

    private String fullsizePath;
    private String thumbnailPath;
    private int maxThumbWidth;
    private int maxThumbHeight;

    public DirectoryManager(PropertiesWrapper props) {
        this.fullsizePath = props.getFullsizePath();
        this.thumbnailPath = props.getThumbnailPath();
        this.maxThumbWidth = props.getThumbnailWidth();
        this.maxThumbHeight = props.getThumbnailHeight();
    }

    public List<ImageInfo> getInfoForAllImages() {
        updateImageDirectories();
        List<String> filenames = getAllFilenamesInDirectory(fullsizePath);
        List<ImageInfo> info = new ArrayList<>();
        for (String name : filenames) {
            String fullImagePath = createAbsolutePath(fullsizePath, name);
            Dimension imageDim = getImageWidthAndHeight(new File(fullImagePath));
            info.add(new ImageInfo(name, imageDim));
        }
        return info;
    }

    public void updateImageDirectories() {
        List<String> fullsizeFilenames = getAllFilenamesInDirectory(fullsizePath);
        List<String> thumbnailFilenames = getAllFilenamesInDirectory(thumbnailPath);
        List<String> thumbnailsNeeded = new ArrayList<>(CollectionUtils.subtract(fullsizeFilenames, thumbnailFilenames));
        Log.info("Thumbnails needed: " + thumbnailsNeeded.toString());

        if (!thumbnailsNeeded.isEmpty()) {
            createAndSaveThumbnails(thumbnailsNeeded);
        }
    }

    private void createAndSaveThumbnails(List<String> thumbnailsNeeded) {
        for(String s : thumbnailsNeeded) {
            BufferedImage img = loadFullsizeBufferedImage(s);
            Log.info("Loaded " + s + " and attempting to scale...");
            BufferedImage scaledImg = ImageUtils.scaleToThumbnail(img, maxThumbWidth,
                    maxThumbHeight);
            File f = new File(createAbsolutePath(thumbnailPath, s));
            try {
                f.createNewFile();
                ImageIO.write(scaledImg, "jpg", f);
            } catch (IOException e) {
                throw new PhotoWsException("Exception thrown while trying to create thumbnail", e);
            }
        }
    }

    private BufferedImage loadFullsizeBufferedImage(String imageName) {
        String path = createAbsolutePath(fullsizePath, imageName);
        Log.debug("Loading " + imageName + " from " + path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new PhotoWsException("IO Exception while loading image", e);
        }
        return img;
    }

    private Dimension getImageWidthAndHeight(File img) {
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

    private List<String> getAllFilenamesInDirectory(String path) {
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
