package ash.java.photo;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;

/**
 * Created by Asher on 20/03/2016.
 */
public class ImageUtils {

    private ImageUtils() {}

    public static BufferedImage scaleToThumbnail(BufferedImage img, int maxWidth, int maxHeight) {
        try {
            if (img.getWidth() >= img.getHeight()) {
                return Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, maxWidth, maxHeight);
            } else {
                return Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_HEIGHT, maxWidth, maxHeight);
            }
        } catch (IllegalArgumentException | ImagingOpException e) {
            throw new PhotoWsException("Exception while creating thumbnail", e);
        }

    }

    public static ImageInfo getInfo(BufferedImage img, String name) {
        int w = img.getWidth();
        int h = img.getHeight();
        return new ImageInfo(name, w, h);
    }

}
