import java.util.List;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product("1", "Headphones");
        Product product2 = new Product("2", "Mouse");
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderRepo = new OrderMapRepo();
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);

        ShopService shopService = new ShopService(orderRepo, productRepo);

        List<String> productIds = List.of("1", "2");
        List<String> productIds2 = List.of("1");

        try {
            shopService.addOrder(productIds);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        try {
            shopService.addOrder(productIds2);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING) = " + shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING));

        try {
          shopService.updateOrder("2", OrderStatus.COMPLETED);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("PROCESSING = " + shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING));
        System.out.println("COMPLETED = " + shopService.getAllOrdersWithStatus(OrderStatus.COMPLETED));

    }
}
