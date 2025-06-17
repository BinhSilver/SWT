package binhnpt.example;

import java.util.logging.Logger;

public class InsecureLogin {

    private static final Logger logger = Logger.getLogger(InsecureLogin.class.getName());

    public static void login(String username, String password) {
        if (username == null || password == null) {
            logger.warning("Username or password is null");
            return;
        }
        if (username.equals("admin") && password.equals(System.getenv("ADMIN_PASSWORD"))) {
            logger.info("Login successful");
        } else {
            logger.warning("Login failed");
        }
    }

    public void printUserInfo(String user) {
        if (user == null || user.isEmpty()) {
            logger.warning("User is null or empty");
            return;
        }
        logger.info("User: " + user);
    }

    // Xoá phương thức không dùng nếu không cần
}