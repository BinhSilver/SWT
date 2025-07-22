package binhnpt.example;

import java.io.*;
import java.util.logging.Logger;

public class PathTraversalExample {
    private static final Logger logger = Logger.getLogger(PathTraversalExample.class.getName());
    public static void main(String[] args) {
        String userInput = "../secret.txt";
        File baseDir = new File("src/main/resources");
        File file = new File(baseDir, userInput);
        try {
            String canonicalBase = baseDir.getCanonicalPath();
            String canonicalFile = file.getCanonicalPath();
            if (!canonicalFile.startsWith(canonicalBase)) {
                logger.warning("Access denied: Path traversal attempt detected.");
                return;
            }
            if (file.exists()) {
                try (BufferedReader ignored = new BufferedReader(new FileReader(file))) {
                    logger.info("Reading file: " + file.getPath());
                }
            } else {
                logger.warning("File does not exist: " + file.getPath());
            }
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
        }
    }
}