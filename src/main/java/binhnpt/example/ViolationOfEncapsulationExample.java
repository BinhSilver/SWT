package binhnpt.example;

    public void setAge(int age) {
        this.age = age;
    }

    public void display() {
        LOGGER.info("Name: " + name + ", Age: " + age);
    }

    // Demo sử dụng class User
    public static void main(String[] args) {
        User user = new User("Alice", 25);
        user.display();
    }
}
