package binhnpt.example;

class Printer {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Printer.class.getName());
    void print() {
        LOGGER.info("Generating report...");
    }
}

class Report {
    private final Printer printer = new Printer(); // tightly coupled

    void generate() {
        printer.print();
    }
}

public class TightCouplingExample {
    public static void main(String[] args) {
        Report report = new Report();
        report.generate();
    }
}
