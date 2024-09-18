import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();
    private int orderId = 1;

    public ShopService(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty() ) {
                throw new IllegalArgumentException("Product with id " + productId + " not found");
            }
            products.add(productToOrder.get());
        }
        Order newOrder = new Order(String.valueOf(orderId), products, OrderStatus.PROCESSING, Instant.now());
        orderId++;
        orderRepo.addOrder(newOrder);
        return newOrder;
    }

    public List<Order> getAllOrdersWithStatus(OrderStatus status) {
         return orderRepo.getOrders()
                 .stream()
                 .filter(order -> order.status().equals(status))
                 .toList();
    }

    public void updateOrder(String orderId, OrderStatus newStatus) {
        Order order = orderRepo.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order with ID: " + orderId + " not found");
        }
        Order updatedOrder = order.withStatus(newStatus);
        orderRepo.updateOrder(updatedOrder);
    }

}
