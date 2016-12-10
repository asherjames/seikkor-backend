package ash.java.photo;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/photo")
public class SeikkorResource {

    @GET
    @Path("/allphotoinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPhotoPaths() {
        PropertiesWrapper wrapper = new PropertiesWrapper(PropertiesLoader.loadProperties());
        DirectoryManager manager = new DirectoryManager(wrapper);
        List<ImageInfo> filenames = manager.getInfoForAllImages();

        Gson gson = new Gson();
        return Response.ok(gson.toJson(filenames)).build();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getTest() {
        return Response.ok("Test!").build();
    }
}
