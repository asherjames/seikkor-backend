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
    @Path("/allphotoinfo")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllPhotoPaths();

}
