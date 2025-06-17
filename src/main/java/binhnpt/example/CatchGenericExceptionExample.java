package binhnpt.example;

import java.util.logging.Logger;

public class CatchGenericExceptionExample {
    private static final Logger logger = Logger.getLogger(CatchGenericExceptionExample.class.getName());

    public static void main(String[] args) {
        String s = (args.length > 0) ? args[0] : null;
        if (s != null) {
            logger.info(() -> String.format("Length: %d", s.length()));
        } else {
            logger.warning("String 's' is null. Cannot get length.");
        }
    }
}
