package ash.java.photo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public interface SeikkorResource {

    @GET
    @Path("/allphotoinfo")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllPhotoPaths();

}
