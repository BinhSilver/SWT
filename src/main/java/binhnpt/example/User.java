package binhnpt.example;

import java.util.logging.Logger;
import java.util.logging.Level;

public class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());

    private String name;
    private int age;

    /**
     * Constructs a User with the given name and age.
     * @param name the user's name
     * @param age the user's age
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Displays the user's information using the logger with built-in formatting.
     */
    public void display() {
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Name: {0}, Age: {1}", new Object[]{name, age});
        }
    }

    public static void main(String[] args) {
        User user = new User("Alice", 22);
        user.display();
        logger.info("Tên: " + user.getName());
        logger.info("Tuổi: " + user.getAge());
        user.setName("Bob");
        user.setAge(30);
        user.display();
    }
}
