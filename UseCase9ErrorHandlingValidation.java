import java.util.*;

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("Cannot decrement. Inventory already zero for: " + roomType);
        }
        inventory.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

class BookingService {
    private int counter = 1;

    public String confirmBooking(String customerName, String roomType, InventoryService inventory)
            throws InvalidBookingException {

        if (customerName == null || customerName.isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty");
        }

        inventory.validateRoomType(roomType);
        inventory.validateAvailability(roomType);

        String reservationId = "R" + counter++;
        inventory.decrement(roomType);

        return reservationId;
    }
}

public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService();

        try {
            String r1 = bookingService.confirmBooking("Alice", "Single", inventory);
            System.out.println("Booking Confirmed: " + r1);

            String r2 = bookingService.confirmBooking("Bob", "Suite", inventory);
            System.out.println("Booking Confirmed: " + r2);

            String r3 = bookingService.confirmBooking("Charlie", "Suite", inventory);
            System.out.println("Booking Confirmed: " + r3);

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        try {
            bookingService.confirmBooking("", "Double", inventory);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        try {
            bookingService.confirmBooking("David", "Luxury", inventory);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        inventory.displayInventory();
    }
}
