import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class InventoryService {
    private Map<String, Integer> roomInventory = new HashMap<>();

    InventoryService() {
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Inventory: " + roomInventory);
    }
}

public class UseCase6ReservationConfirmation {
    private Queue<BookingRequest> requestQueue = new LinkedList<>();
    private Set<String> allocatedRooms = new HashSet<>();
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();
    private int roomCounter = 1;

    public void addRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 1).toUpperCase() + roomCounter++;
        } while (allocatedRooms.contains(roomId));
        return roomId;
    }

    public void processRequests(InventoryService inventoryService) {
        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();

            if (inventoryService.isAvailable(request.roomType)) {
                String roomId = generateRoomId(request.roomType);

                allocatedRooms.add(roomId);

                roomAllocationMap
                        .computeIfAbsent(request.roomType, k -> new HashSet<>())
                        .add(roomId);

                inventoryService.decrement(request.roomType);

                System.out.println("Reservation Confirmed: " +
                        request.customerName + " -> Room " + roomId);
            } else {
                System.out.println("Booking Failed: " + request.customerName);
            }
        }
    }

    public void displayAllocations() {
        System.out.println("Room Allocations: " + roomAllocationMap);
    }

    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        UseCase6ReservationConfirmation app = new UseCase6ReservationConfirmation();

        app.addRequest(new BookingRequest("Alice", "Single"));
        app.addRequest(new BookingRequest("Bob", "Double"));
        app.addRequest(new BookingRequest("Charlie", "Single"));
        app.addRequest(new BookingRequest("David", "Suite"));
        app.addRequest(new BookingRequest("Eve", "Suite"));

        app.processRequests(inventoryService);
        app.displayAllocations();
        inventoryService.displayInventory();
    }
}