import java.util.*;

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
        inventory.put("Suite Room", 0);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }
}

class SearchService {
    void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {
        System.out.println("===== Available Rooms =====");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.type);

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

public class UseCase4RoomSearch {
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App (v4.1) =====");

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        RoomInventory inventory = new RoomInventory();

        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(rooms, inventory);
    }
}