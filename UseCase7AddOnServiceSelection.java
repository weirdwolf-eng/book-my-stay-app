import java.util.*;

class AddOnService {
    String serviceName;
    double cost;

    AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
}

public class UseCase7AddOnServiceSelection {
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (AddOnService s : services) {
            total += s.cost;
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("Services for " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println(s.serviceName + " - " + s.cost);
        }
        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }

    public static void main(String[] args) {
        UseCase7AddOnServiceSelection app = new UseCase7AddOnServiceSelection();

        String reservationId1 = "R101";
        String reservationId2 = "R102";

        app.addService(reservationId1, new AddOnService("Breakfast", 500));
        app.addService(reservationId1, new AddOnService("Spa", 1500));
        app.addService(reservationId1, new AddOnService("Airport Pickup", 800));

        app.addService(reservationId2, new AddOnService("Dinner", 700));

        app.displayServices(reservationId1);
        app.displayServices(reservationId2);
    }
}