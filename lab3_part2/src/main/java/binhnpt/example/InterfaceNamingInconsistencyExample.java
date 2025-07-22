package binhnpt.example;

import java.util.logging.Level;
import java.util.logging.Logger;

// Interface định nghĩa đăng nhập
interface LoginHandler {
    boolean login(String username, String password);
}

// Cài đặt đăng nhập đơn giản
class SimpleLoginHandler implements LoginHandler {
    private static final Logger logger = Logger.getLogger(SimpleLoginHandler.class.getName());

    @Override
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Username or password is null.");
            }
            return false;
        }

        if ("admin".equals(username) && "password".equals(password)) {
            if (logger.isLoggable(Level.INFO)) {
                logger.info(String.format("User '%s' logged in successfully.", username));
            }
            return true;
        } else {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning(String.format("Failed login attempt for user '%s'", username));
            }
            return false;
        }
    }
}

class InterfaceAndLoggingConsistencyExample {
    private static final Logger logger = Logger.getLogger(InterfaceAndLoggingConsistencyExample.class.getName());

    public static void main(String[] args) {
        LoginHandler handler = new SimpleLoginHandler();

        boolean adminLogin = handler.login("admin", "password");
        if (adminLogin) {
            if (logger.isLoggable(Level.INFO)) {
                logger.info("Admin login success.");
            }
        } else {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Admin login failed.");
            }
        }

        boolean userLogin = handler.login("user", "username");
        if (userLogin) {
            if (logger.isLoggable(Level.INFO)) {
                logger.info("User login success.");
            }
        } else {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("User login failed.");
            }
        }
    }
}
