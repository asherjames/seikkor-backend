package ash.java.photo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Asher on 20/03/2016.
 */

public interface PhotoResource {

    @GET
    @Path("/filenames")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllPhotoPaths();

    @GET
    @Path("/photo/{photoname}")
    @Produces("image/jpeg")
    Response getFullsizePhoto(@PathParam("photoname") String name);

    @GET
    @Path("/photo/thumb/{photoname}")
    @Produces("image/jpeg")
    Response getThumbnailPhoto(@PathParam("photoname") String name);
}