package ash.java.photo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/photo")
public class SeikkorApplication extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> classes = new HashSet<>();

    public SeikkorApplication() {
        singletons.add(new SeikkorResourceImpl());
        classes.add(CorsFilter.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
