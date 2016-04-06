package ash.java.photo;

import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import java.util.List;


public class SeikkorResourceImpl implements SeikkorResource {

    @Override
    public Response getAllPhotoPaths() {
        PropertiesWrapper wrapper = new PropertiesWrapper(PropertiesLoader.loadProperties());
        DirectoryManager manager = new DirectoryManager(wrapper);
        List<ImageInfo> filenames = manager.getInfoForAllImages();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(filenames)).build();
    }
}
