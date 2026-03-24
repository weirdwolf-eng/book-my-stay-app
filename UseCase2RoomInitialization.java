abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    abstract void displayDetails();
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000.0);
    }

    void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 1800.0);
    }

    void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 3000.0);
    }

    void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

public class UseCase2RoomInitialization {
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (v2.1) =====");

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        r1.displayDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println();

        r2.displayDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println();

        r3.displayDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}