package ash.java.photo;

import com.google.gson.annotations.SerializedName;

import java.awt.*;

/**
 * Created by Asher on 23/03/2016.
 */
public class ImageInfo {

    @SerializedName("src") private String filename;
    @SerializedName("thumbnail") private String thumbnail;
    @SerializedName("w") private int width;
    @SerializedName("h") private int height;

    public ImageInfo(String filename, Dimension dimensions) {
        this.filename = filename;
        this.thumbnail = filename;
        this.width = dimensions.width;
        this.height = dimensions.height;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        if (width != imageInfo.width) return false;
        if (height != imageInfo.height) return false;
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
                '}';
    }
}
