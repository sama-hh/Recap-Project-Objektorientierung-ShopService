import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderRepo = new OrderMapRepo();
        IdService idService = new IdService();

        Product product1 = new Product("1", "Apfel");
        Product product2 = new Product("2", "Banana");
        Product product3 = new Product("3", "Blueberry");
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);

        ShopService shopService = new ShopService(orderRepo, productRepo, idService);

        List<String> productIds = List.of("1", "2");
        List<String> productIds2 = List.of("1");
        List<String> productIds3 = List.of("3");


        try {
            shopService.addOrder(productIds);
            shopService.addOrder(productIds2);
            shopService.addOrder(productIds3);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("PROCESSING = " + shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING));

        Order order = shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING).get(0);

        try {
          shopService.updateOrder(order.id(), OrderStatus.COMPLETED);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("PROCESSING = " + shopService.getAllOrdersWithStatus(OrderStatus.PROCESSING));
        System.out.println("COMPLETED= " + shopService.getAllOrdersWithStatus(OrderStatus.COMPLETED));

    }
}
