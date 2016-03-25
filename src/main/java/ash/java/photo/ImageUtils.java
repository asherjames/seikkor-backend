package ash.java.photo;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * Created by Asher on 20/03/2016.
 */
public class ImageUtils {

    public static int scaleImage(BufferedImage img) {

    }

    public static ImageInfo getInfo(BufferedImage img, String name) {
        int w = img.getWidth();
        int h = img.getHeight();
        return new ImageInfo(name, w, h);
    }

}
