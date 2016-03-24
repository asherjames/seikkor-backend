package ash.java.photo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asher on 23/03/2016.
 */
public class ImageInfo {

    @SerializedName("src") private String filename;
    @SerializedName("w") private int width;
    @SerializedName("h") private int height;

    public ImageInfo(String filename, int width, int height) {
        this.filename = filename;
        this.width = width;
        this.height = height;
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

}
