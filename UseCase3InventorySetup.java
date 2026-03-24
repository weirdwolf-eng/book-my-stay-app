import java.util.HashMap;

abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000.0);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 1800.0);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 3000.0);
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> Available: " + inventory.get(key));
        }
    }
}

public class UseCase3InventorySetup {
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (v3.1) =====");

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        r1.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(r1.type));
        System.out.println();

        r2.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(r2.type));
        System.out.println();

        r3.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(r3.type));
        System.out.println();

        inventory.displayInventory();
    }
}
