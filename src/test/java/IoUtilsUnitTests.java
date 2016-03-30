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

    @Test
    public void propertiesAreCorrectlyLoaded() {
        Properties props = IoUtils.loadProperties();

        assertThat(props.getProperty("fullsizeImageFolderPath"), is("images"));
    }

    @Test
    public void imageDirectoriesAreCorrectlyUpdated() {
        Properties props = mock(Properties.class);
        when(props.getProperty("fullsizeImageFolderPath")).thenReturn("C:\\images\\fullsize");
        when(props.getProperty("thumbnailImageFolderPath")).thenReturn("C:\\images\\thumbnail");

        when(props.getProperty("maxThumbnailWidth")).thenReturn("250");
        when(props.getProperty("maxThumbnailHeight")).thenReturn("400");

        IoUtils.updateImageDirectories(props);
    }
}
