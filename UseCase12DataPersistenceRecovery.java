import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String reservationId;
    String customerName;
    String roomType;

    Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class InventoryService implements Serializable {
    Map<String, Integer> inventory = new HashMap<>();

    InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

class SystemState implements Serializable {
    List<Reservation> reservations;
    InventoryService inventory;

    SystemState(List<Reservation> reservations, InventoryService inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("State Saved");
        } catch (Exception e) {
            System.out.println("Error saving state");
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("State Loaded");
            return (SystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load();

        List<Reservation> reservations;
        InventoryService inventory;

        if (state != null) {
            reservations = state.reservations;
            inventory = state.inventory;
        } else {
            reservations = new ArrayList<>();
            inventory = new InventoryService();
        }

        reservations.add(new Reservation("R101", "Alice", "Single"));
        reservations.add(new Reservation("R102", "Bob", "Double"));

        System.out.println("Current Reservations:");
        for (Reservation r : reservations) {
            System.out.println(r.reservationId + " " + r.customerName + " " + r.roomType);
        }

        inventory.displayInventory();

        persistence.save(new SystemState(reservations, inventory));
    }
}