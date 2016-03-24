package ash.java.photo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Asher on 20/03/2016.
 */

@ApplicationPath("/photo")
public class PhotoApplication extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> empty = new HashSet<>();

    public PhotoApplication() {
        singletons.add(new PhotoResourceImpl());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
