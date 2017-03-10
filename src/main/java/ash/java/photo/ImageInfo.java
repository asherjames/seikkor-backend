package ash.java.photo;

import com.google.gson.annotations.SerializedName;

import java.awt.Dimension;


public class ImageInfo {

    @SerializedName("src") private String filename;
    @SerializedName("thumbnail") private String thumbnail;
    @SerializedName("w") private int width;
    @SerializedName("h") private int height;
    @SerializedName("thumbW") private int thumbnailWidth;
    @SerializedName("thumbH") private int thumbnailHeight;

    public ImageInfo(String filename, Dimension dimensions, Dimension thumbnailDimensions) {
        this.filename = filename;
        this.thumbnail = filename;
        this.width = dimensions.width;
        this.height = dimensions.height;
        this.thumbnailWidth = thumbnailDimensions.width;
        this.thumbnailHeight = thumbnailDimensions.height;
    }

    public String getFilename() {
        return filename;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        if (width != imageInfo.width) return false;
        if (height != imageInfo.height) return false;
        if (thumbnailHeight != imageInfo.thumbnailHeight) return false;
        if (thumbnailWidth != imageInfo.thumbnailWidth) return false;
        if (filename != null ? !filename.equals(imageInfo.filename) : imageInfo.filename != null) return false;
        return thumbnail != null ? thumbnail.equals(imageInfo.thumbnail) : imageInfo.thumbnail == null;

    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "filename='" + filename + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", thumbnailWidth=" + thumbnailWidth +
                ", thumbnailHeight=" + thumbnailHeight +
                '}';
    }
}
