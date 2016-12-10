package ash.java.photo;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeikkorConfig extends ResourceConfig {
    public SeikkorConfig() {
        register(SeikkorResource.class);
    }
}
