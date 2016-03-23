package ash.java.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Asher on 23/03/2016.
 */
public class PhotoWsException extends RuntimeException {

    private static final Logger Log = LoggerFactory.getLogger(PhotoWsException.class);

    public PhotoWsException(String comment, Throwable throwable) {
        super(comment, throwable);
        Log.error(comment, throwable.getMessage());
    }
}
