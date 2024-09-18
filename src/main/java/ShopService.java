import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();
    private IdService IdService = new IdService();

    public ShopService(OrderRepo orderRepo, ProductRepo productRepo, IdService IdService) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.IdService = IdService;
    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty() ) {
                throw new IllegalArgumentException("Product with id " + productId + " not found");
            } else {
                products.add(productToOrder.get());
                int newQuantity = productToOrder.get().quantity() - 1;
                Product updatedProduct = productToOrder.get().withQuantity(newQuantity);
                productRepo.updateProduct(updatedProduct);
            }
        }
        Order newOrder = new Order(IdService.generateId(), products, OrderStatus.PROCESSING, Instant.now());
        orderRepo.addOrder(newOrder);
        return newOrder;
    }

    public List<Order> getAllOrdersWithStatus(OrderStatus status) {
         return orderRepo.getOrders()
                 .stream()
                 .filter(order -> order.status().equals(status))
                 .toList();
    }

    public Order updateOrder(String orderId, OrderStatus newStatus) {
        Order order = orderRepo.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order with ID: " + orderId + " not found");
        }
        Order updatedOrder = order.withStatus(newStatus);
        orderRepo.updateOrder(updatedOrder);
        return updatedOrder;
    }

    public Order getOldestOrderPerStatus(OrderStatus status) {
        return getAllOrdersWithStatus(status).stream()
                .min((o1,o2) -> o1.timestamp().compareTo(o2.timestamp()))
                .orElseThrow(() -> new NoSuchElementException("No order found"));
    }

}
