import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();

        Product product1 = new Product("1", "Apfel");
        Product product2 = new Product("2", "Banana");
        Product product3 = new Product("3", "Blueberry");
        Product product4 = new Product("4", "Plum");
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);
        productRepo.addProduct(product4);

        ShopService shopService = new ShopService(orderRepo, productRepo, idService);

        List<String> productIds = List.of("1");
        List<String> productIds2 = List.of("2");
        List<String> productIds3 = List.of("3");


        try {
            shopService.addOrder(productIds);
//            shopService.addOrder(productIds2);
//            shopService.addOrder(productIds3);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

//        System.out.println("PROCESSING = " + shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING));

        Order order = shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING).get(0);

        try {
          shopService.updateOrder(order.id(), OrderStatus.COMPLETED);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

//        System.out.println("Update");
//        System.out.println("PROCESSING = " + shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING));
//        System.out.println("COMPLETED= " + shopService.getAllOrdersWithStatus(OrderStatus.COMPLETED));
//
//        System.out.println("oldest order = " + shopService.getOldestOrderPerStatus(OrderStatus.PROCESSING));

        // Transaction File
        HashMap<String, Order> ordersMap = new HashMap<>();
        try {
            Path filePath = Path.of("transactions.txt");
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                processCommand(line, shopService, ordersMap);
            }

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void processCommand(String line, ShopService shopService, HashMap<String, Order> ordersMap) {
        String[] parts = line.split(" ");
        System.out.println(Arrays.toString(parts));

        switch (parts[0]) {
            case "addOrder" -> {
                String orderName = parts[1];
                List<String> productIds = Arrays.asList(parts).subList(2, parts.length);
                Order order = shopService.addOrder(productIds);
                ordersMap.put(orderName, order);
            }
            case "setStatus" -> {
                String orderName = parts[1];
                String status = parts[2];
                Order updatedOrder = shopService.updateOrder(ordersMap.get(orderName).id(), OrderStatus.COMPLETED);
                ordersMap.replace(orderName, updatedOrder);
            }
            case "printOrders" -> {
                System.out.println(ordersMap);
            }
        }
    };
}
