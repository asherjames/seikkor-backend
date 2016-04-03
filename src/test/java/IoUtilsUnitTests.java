import ash.java.photo.ImageInfo;
import ash.java.photo.IoUtils;
import ash.java.photo.PhotoWsException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Asher on 28/03/2016.
 */
public class IoUtilsUnitTests {

    private final String TEST_FULLSIZE_FOLDER = "C:\\images\\fullsize";
    private final String TEST_THUMBNAIL_FOLDER = "C:\\images\\thumbnail";

    private static Properties props;

    private final Logger Log = LoggerFactory.getLogger(IoUtilsUnitTests.class);

    @BeforeClass
    public static void mockProperties() {
        props = mock(Properties.class);
        when(props.getProperty("fullsizeImageFolderPath")).thenReturn("C:\\images\\fullsize");
        when(props.getProperty("thumbnailImageFolderPath")).thenReturn("C:\\images\\thumbnail");
        when(props.getProperty("maxThumbnailWidth")).thenReturn("250");
        when(props.getProperty("maxThumbnailHeight")).thenReturn("400");
    }

    @Before
    public void prepareDirectories() {
        createDummyImages(TEST_FULLSIZE_FOLDER, 5);
    }

    /*@After
    public void cleanupDirectories() {
        deleteDirectoryContents(TEST_FULLSIZE_FOLDER);
        deleteDirectoryContents(TEST_THUMBNAIL_FOLDER);
    }*/

    @Test
    public void propertiesAreCorrectlyLoaded() {
        Properties props = IoUtils.loadProperties();

        assertThat(props.getProperty("fullsizeImageFolderPath"), is("C:/images/fullsize"));
    }

    @Test
    public void imageDirectoriesAreCorrectlyUpdated() {
        IoUtils.updateImageDirectories(props);

        assertThat(checkDirectoriesContainSameFilenames(TEST_FULLSIZE_FOLDER, TEST_THUMBNAIL_FOLDER), is(true));
    }

    @Test
    public void imageInfoArrayHasCorrectLength() {
        List<ImageInfo> imageInfos = IoUtils.getInfoForAllImages(props);
        Log.info("First image data: " + imageInfos.get(0).toString());
        assertThat(imageInfos, iterableWithSize(5));
        deleteDirectoryContents(TEST_FULLSIZE_FOLDER);
        deleteDirectoryContents(TEST_THUMBNAIL_FOLDER);
    }

    @Test
    public void imageInfoArrayContainsCorrectImageInfoObject() {
        List<ImageInfo> imageInfos = IoUtils.getInfoForAllImages(props);
        ImageInfo testInfo = new ImageInfo("0.jpg", new Dimension(1000, 1500));
        Log.info("ImageInfo from IoUtils:" + imageInfos.get(0).toString());
        Log.info("ImageInfo from test:" + testInfo.toString());
        assertThat(imageInfos.get(0), equalTo(testInfo));
    }

    private boolean checkDirectoriesContainSameFilenames(String firstFolder, String secondFolder) {
        File imageFolder1 = new File(firstFolder);
        File imageFolder2 = new File(secondFolder);
        String[] firstFilenames = imageFolder1.list();
        String[] secondFilenames = imageFolder2.list();
        return Arrays.deepEquals(firstFilenames, secondFilenames);
    }

    private void createDummyImages(String path, int numOfImgs) {
        try {
            for (int i = 0; i < numOfImgs; i++) {
                BufferedImage img = new BufferedImage(1000, 1500, BufferedImage.TYPE_INT_RGB);
                String filename = path + "/" + i + ".jpg";
                ImageIO.write(img, "jpg", new File(filename));
            }
        } catch (IOException e) {
            Log.error("IOException while trying to create dummy images");
        }
    }

    private void deleteDirectoryContents(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            throw new PhotoWsException("IO exception while attempting to delete directory contents", e);
        }
    }
}
