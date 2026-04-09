import java.util.*;

class Reservation {
    String reservationId;
    String roomType;
    boolean isActive;

    Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.isActive = true;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

class CancellationService {
    private Map<String, Reservation> reservationMap = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    public void addReservation(Reservation reservation) {
        reservationMap.put(reservation.reservationId, reservation);
    }

    public void cancel(String reservationId, InventoryService inventory) {
        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid Reservation ID");
            return;
        }

        Reservation reservation = reservationMap.get(reservationId);

        if (!reservation.isActive) {
            System.out.println("Cancellation Failed: Already Cancelled");
            return;
        }

        rollbackStack.push(reservationId);

        inventory.increment(reservation.roomType);

        reservation.isActive = false;

        System.out.println("Cancellation Successful: " + reservationId);
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class UseCase10BookingCancellationRollback {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        CancellationService cancellationService = new CancellationService();

        Reservation r1 = new Reservation("R101", "Single");
        Reservation r2 = new Reservation("R102", "Double");

        cancellationService.addReservation(r1);
        cancellationService.addReservation(r2);

        cancellationService.cancel("R101", inventory);
        cancellationService.cancel("R101", inventory);
        cancellationService.cancel("R999", inventory);

        cancellationService.displayRollbackStack();
        inventory.displayInventory();
    }
}