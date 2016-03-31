package ash.java.photo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Properties;

/**
 * Created by Asher on 20/03/2016.
 */
public class PhotoResourceImpl implements PhotoResource {

    private final Logger Log = LoggerFactory.getLogger(PhotoResourceImpl.class);

    public Response getAllPhotoPaths() {
        Properties props = IoUtils.loadProperties();
        List<ImageInfo> filenames = IoUtils.getInfoForAllImages(props);
        Gson gson = new Gson();
        Log.info("Attempting to convert filename ArrayList to JSON...");
        return Response.ok(gson.toJson(filenames)).build();
    }
}
