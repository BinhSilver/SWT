package binhnpt.example;

public class NullPointerExample {
    public static void main(String[] args) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NullPointerExample.class.getName());
        String text = (args.length > 0) ? args[0] : "";
        if (!text.isEmpty()) {
            logger.info("Text is not empty");
        }
    }
}
