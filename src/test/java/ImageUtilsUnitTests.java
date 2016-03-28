import ash.java.photo.ImageInfo;
import ash.java.photo.ImageUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.awt.image.BufferedImage;

/**
 * Created by Asher on 28/03/2016.
 */
public class ImageUtilsUnitTests {

    private static BufferedImage imgLongerW;
    private static BufferedImage imgLongerH;
    private static BufferedImage squareImg;

    @BeforeClass
    public static void createBufferedImage() {
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

    @Test
    public void imageInfoObjectHasCorrectAttributes() {
        BufferedImage img = new BufferedImage(150, 100, BufferedImage.TYPE_INT_RGB);

        ImageInfo info = ImageUtils.getInfo(img, "image01");

        assertThat(info, hasProperty("filename", equalTo("image01")));
        assertThat(info, hasProperty("width", equalTo(150)));
        assertThat(info, hasProperty("height", equalTo(100)));
    }
}
