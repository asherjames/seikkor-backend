import ash.java.photo.*;
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
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class DirectoryManagerUnitTest {

    private static String TEST_FULLSIZE_FOLDER;
    private static String TEST_THUMBNAIL_FOLDER;

    private static PropertiesWrapper wrapper;
    private static DirectoryManager manager;

    private final Logger Log = LoggerFactory.getLogger(DirectoryManagerUnitTest.class);

    @BeforeClass
    public static void mockProperties() {
        createTestImageFolders();
        TEST_FULLSIZE_FOLDER = getFullsizeFolderPath();
        TEST_THUMBNAIL_FOLDER = getThumbnailFolderPath();
        wrapper = mock(PropertiesWrapper.class);
        when(wrapper.getFullsizePath()).thenReturn(TEST_FULLSIZE_FOLDER);
        when(wrapper.getThumbnailPath()).thenReturn(TEST_THUMBNAIL_FOLDER);
        when(wrapper.getThumbnailWidth()).thenReturn(200);
        when(wrapper.getThumbnailHeight()).thenReturn(300);
        manager = new DirectoryManager(wrapper);
    }

    @Before
    public void prepareDirectories() {
        createDummyImages(TEST_FULLSIZE_FOLDER, 5);
    }

    @After
    public void cleanupDirectories() {
        deleteDirectoryContents(TEST_FULLSIZE_FOLDER);
        deleteDirectoryContents(TEST_THUMBNAIL_FOLDER);
    }

    @Test
    public void propertiesAreCorrectlyLoaded() {
        Properties props = PropertiesLoader.loadProperties();

        assertThat(props.getProperty("fullsizeImageFolderPath"), is("C:/images/fullsize"));
    }

    @Test
    public void imageDirectoriesAreCorrectlyUpdated() {
        manager.updateImageDirectories();

        assertThat(checkDirectoriesContainSameFilenames(TEST_FULLSIZE_FOLDER, TEST_THUMBNAIL_FOLDER), is(true));
    }

    @Test
    public void imageInfoArrayHasCorrectLength() {
        List<ImageInfo> imageInfos = manager.getInfoForAllImages();
        Log.info("First image data: " + imageInfos.get(0).toString());
        assertThat(imageInfos, iterableWithSize(5));
        deleteDirectoryContents(TEST_FULLSIZE_FOLDER);
        deleteDirectoryContents(TEST_THUMBNAIL_FOLDER);
    }

    @Test
    public void imageInfoArrayContainsCorrectImageInfoObject() {
        List<ImageInfo> imageInfos = manager.getInfoForAllImages();
        ImageInfo testInfo = new ImageInfo("0.jpg", new Dimension(1000, 1500), new Dimension(200, 300));
        Log.info("ImageInfo from DirectoryManager:" + imageInfos.get(0).toString());
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

    private static String getFullsizeFolderPath() {
        return new File("src/test/resources/fullsize").getAbsolutePath();
    }

    private static String getThumbnailFolderPath() {
        return new File("src/test/resources/thumbnail").getAbsolutePath();
    }

    private static void createTestImageFolders() {
        File fullsizeDir = new File("src/test/resources/fullsize");
        File thumbnailDir = new File("src/test/resources/thumbnail");
        if (!fullsizeDir.exists()) {
            fullsizeDir.mkdir();
        }
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdir();
        }
    }
}
