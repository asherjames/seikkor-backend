package ash.java.photo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Asher on 20/03/2016.
 */
public class SeikkorResourceImpl implements SeikkorResource {

    private final Logger Log = LoggerFactory.getLogger(SeikkorResourceImpl.class);

    @Override
    public Response getAllPhotoPaths() {
        PropertiesWrapper wrapper = new PropertiesWrapper(PropertiesLoader.loadProperties());
        List<ImageInfo> filenames = DirectoryManager.getInfoForAllImages(wrapper);

        Gson gson = new Gson();
        Log.info("Attempting to convert filename ArrayList to JSON...");
        return Response.ok(gson.toJson(filenames)).build();
    }
}
