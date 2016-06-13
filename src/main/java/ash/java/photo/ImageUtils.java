package ash.java.photo;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;


public class ImageUtils {

    private static final Logger Log = LoggerFactory.getLogger(ImageUtils.class);

    private ImageUtils() {}

    public static BufferedImage scaleToThumbnail(BufferedImage img, int maxWidth, int maxHeight) {
        try {
            if (img.getWidth() >= img.getHeight()) {
                Log.info("Img width larger than height, scaling accordingly...");
                return Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, maxWidth, maxHeight);
            } else {
                Log.info("Img height larger than width, scaling accordingly...");
                return Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_HEIGHT, maxWidth, maxHeight);
            }
        } catch (IllegalArgumentException | ImagingOpException e) {
            throw new PhotoWsException("Incorrect usage of Scalr, returning default image...", e);
        }
    }
}
