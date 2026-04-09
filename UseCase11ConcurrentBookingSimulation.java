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
    private Map<String, Integer> inventory = new HashMap<>();

    InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public synchronized boolean allocateRoom(String roomType) {
        int count = inventory.getOrDefault(roomType, 0);
        if (count <= 0) return false;
        inventory.put(roomType, count - 1);
        return true;
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

class BookingProcessor implements Runnable {
    private Queue<BookingRequest> queue;
    private InventoryService inventory;

    BookingProcessor(Queue<BookingRequest> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                request = queue.poll();
            }

            if (inventory.allocateRoom(request.roomType)) {
                System.out.println(Thread.currentThread().getName() +
                        " booked " + request.roomType +
                        " for " + request.customerName);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed for " + request.customerName);
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        Queue<BookingRequest> queue = new LinkedList<>();
        InventoryService inventory = new InventoryService();

        queue.offer(new BookingRequest("Alice", "Single"));
        queue.offer(new BookingRequest("Bob", "Single"));
        queue.offer(new BookingRequest("Charlie", "Single"));
        queue.offer(new BookingRequest("David", "Suite"));
        queue.offer(new BookingRequest("Eve", "Suite"));

        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(queue, inventory), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}