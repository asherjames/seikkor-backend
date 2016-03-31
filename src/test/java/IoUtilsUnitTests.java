import ash.java.photo.IoUtils;
import org.junit.Test;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Properties;

/**
 * Created by Asher on 28/03/2016.
 */
public class IoUtilsUnitTests {

    private final String TEST_FULLSIZE_FOLDER = "C:\\images\\fullsize";
    private final String TEST_THUMBNAIL_FOLDER = "C:\\images\\thumbnail";
    private final String TEST_MAX_THUMBNAIL_WIDTH = "250";
    private final String TEST_MAX_THUMBNAIL_HEIGHT = "400";

    @Test
    public void propertiesAreCorrectlyLoaded() {
        Properties props = IoUtils.loadProperties();

        assertThat(props.getProperty("fullsizeImageFolderPath"), is("images"));
    }

    @Test
    public void imageDirectoriesAreCorrectlyUpdated() {
        Properties props = mock(Properties.class);
        when(props.getProperty("fullsizeImageFolderPath")).thenReturn(TEST_FULLSIZE_FOLDER);
        when(props.getProperty("thumbnailImageFolderPath")).thenReturn(TEST_THUMBNAIL_FOLDER);
        when(props.getProperty("maxThumbnailWidth")).thenReturn(TEST_MAX_THUMBNAIL_WIDTH);
        when(props.getProperty("maxThumbnailHeight")).thenReturn(TEST_MAX_THUMBNAIL_HEIGHT);

        IoUtils.updateImageDirectories(props);
    }
}
