package ash.java.photo;

import java.util.Properties;

public class PropertiesWrapper {

    private Properties props;
    private final String FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY = "fullsizeImageFolderPath";
    private final String THUMBNAIL_IMAGE_FOLDER_PATH_PROPERTY = "thumbnailImageFolderPath";
    private final String THUMBNAIL_MAX_WIDTH_PROPERTY = "maxThumbnailWidth";
    private final String THUMBNAIL_MAX_HEIGHT_PROPERTY = "maxThumbnailHeight";

    public PropertiesWrapper(Properties props) {
        this.props = props;
    }

    public String getFullsizePath() {
        return props.getProperty(FULLSIZE_IMAGE_FOLDER_PATH_PROPERTY);
    }

    public String getThumbnailPath() {
        return props.getProperty(THUMBNAIL_IMAGE_FOLDER_PATH_PROPERTY);
    }

    public int getThumbnailWidth() {
        String widthString = props.getProperty(THUMBNAIL_MAX_WIDTH_PROPERTY);
        try {
            return Integer.parseInt(widthString);
        } catch (NumberFormatException e) {
            throw new PhotoWsException("Properties conversion exception", e);
        }
    }

    public int getThumbnailHeight() {
        String heightString = props.getProperty(THUMBNAIL_MAX_HEIGHT_PROPERTY);
        try {
            return Integer.parseInt(heightString);
        } catch (NumberFormatException e) {
            throw new PhotoWsException("Properties conversion exception", e);
        }
    }
}
