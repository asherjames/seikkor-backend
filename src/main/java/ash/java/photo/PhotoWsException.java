package ash.java.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PhotoWsException extends RuntimeException {

    private static final Logger Log = LoggerFactory.getLogger(PhotoWsException.class);

    public PhotoWsException(String comment, Throwable throwable) {
        super(comment, throwable);
        Log.error(comment, throwable.getMessage());
    }

    public PhotoWsException(String comment) {
        super(comment);
        Log.error(comment);
    }
}
