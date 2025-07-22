package binhnpt.example;

class Animal {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Animal.class.getName());
    void speak() {
        LOGGER.info("Animal speaks");
    }
}

class Dog extends Animal {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Dog.class.getName());
    void bark() {
        LOGGER.info("Dog barks");
    }
}

public class MissingOverrideAnnotationExample {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.speak();
        Dog dog = new Dog();
        dog.speak();
        dog.bark();
    }
}
