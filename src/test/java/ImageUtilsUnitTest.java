import ash.java.photo.ImageUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.awt.image.BufferedImage;

/**
 * Created by Asher on 28/03/2016.
 */
public class ImageUtilsUnitTest {

    private static BufferedImage imgLongerW;
    private static BufferedImage imgLongerH;
    private static BufferedImage squareImg;

    private static final Logger Log = LoggerFactory.getLogger(ImageUtilsUnitTest.class);

    @BeforeClass
    public static void createBufferedImage() {
        Log.info("Creating test BufferedImage's");
        imgLongerW = new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB);
        imgLongerH = new BufferedImage(150, 200, BufferedImage.TYPE_INT_RGB);
        squareImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    public void imageWithLongerWidthIsCorrectlyScaled() {
        BufferedImage returnedImage = ImageUtils.scaleToThumbnail(imgLongerW, 50, 100);

        assertThat(returnedImage.getWidth(), greaterThan(returnedImage.getHeight()));
    }

    @Test
    public void imageWithLongerHeightIsCorrectlyScaled() {
        BufferedImage returnedImage = ImageUtils.scaleToThumbnail(imgLongerH, 50, 100);

        assertThat(returnedImage.getWidth(), lessThan(returnedImage.getHeight()));
    }

    @Test
    public void imageWithLongerWidthIsScaledWithCorrectDimensions() {
        BufferedImage returnedImage = ImageUtils.scaleToThumbnail(imgLongerW, 50, 100);

        assertThat(returnedImage.getWidth(), is(50));
    }


    @Test
    public void imageWithLongerHeightIsScaledWithCorrectDimensions() {
        BufferedImage returnedImage = ImageUtils.scaleToThumbnail(imgLongerH, 50, 100);

        assertThat(returnedImage.getHeight(), is(100));
    }

    @Test
    public void squareImageIsScaledWithCorrectDimensions() {
        BufferedImage returnedImage = ImageUtils.scaleToThumbnail(squareImg, 50, 100);

        assertThat(returnedImage.getWidth(), is(50));
        assertThat(returnedImage.getHeight(), is(50));
    }
}
