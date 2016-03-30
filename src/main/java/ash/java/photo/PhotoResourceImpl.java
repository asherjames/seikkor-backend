package ash.java.photo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Asher on 20/03/2016.
 */
public class PhotoResourceImpl implements PhotoResource {

    private final Logger Log = LoggerFactory.getLogger(PhotoResourceImpl.class);

    public Response getAllPhotoPaths() {
        List<ImageInfo> filenames = IoUtils.getInfoForAllImages();
        Gson gson = new Gson();
        Log.info("Attempting to convert filename ArrayList to JSON...");
        return Response.ok(gson.toJson(filenames)).build();
    }

    /*public Response getFullsizePhoto(String filename) {
        BufferedImage img = IoUtils.loadImage(filename, ImageTypeEnum.FULLSIZE);
        if (img != null) {
            Log.info("Fullsize image found, returning image...");
            return Response.ok(img).build();
        } else {
            Log.info("Fullsize image not found, returning 400...");
            return Response.status(400).build();
        }
    }

    public Response getThumbnailPhoto(String filename) {
        BufferedImage img = IoUtils.loadImage(filename, ImageTypeEnum.THUMBNAIL);
        if (img != null) {
            Log.info("Thumbnail image found, returning image...");
            return Response.ok(img).build();
        } else {
            Log.info("Thumbnail image not found, returning 400...");
            return Response.status(400).build();
        }
    }*/
}
