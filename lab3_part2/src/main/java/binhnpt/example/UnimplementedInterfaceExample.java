package binhnpt.example;

interface Drawable {
    void draw();
}

class Circle implements Drawable {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Circle.class.getName());
    @Override
    public void draw() {
        LOGGER.info("Drawing a circle.");
    }
}

public class UnimplementedInterfaceExample {
    public static void main(String[] args) {
        Drawable shape = new Circle();
        shape.draw();
    }
}
