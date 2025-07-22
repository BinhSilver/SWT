package binhnpt.example;

class Constants {
    public static final int MAX_USERS = 100;

    private Constants() {
        // Prevent instantiation
    }
}

public class InterfaceFieldModificationExample {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(InterfaceFieldModificationExample.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Max users allowed: " + Constants.MAX_USERS);
    }
}
