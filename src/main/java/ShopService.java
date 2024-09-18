import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService IdService;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                                                .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " not found"));
            products.add(productToOrder);
            int newQuantity = productToOrder.quantity() - 1;
            Product updatedProduct = productToOrder.withQuantity(newQuantity);
            productRepo.updateProduct(updatedProduct);
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
            throw new NoSuchElementException("Order with ID: " + orderId + " not found");
        }
        Order updatedOrder = order.withStatus(newStatus);
        orderRepo.updateOrder(updatedOrder);
        return updatedOrder;
    }

    public HashMap<OrderStatus, Order> getOldestOrderPerStatus(OrderStatus status) {
        HashMap<OrderStatus, Order> orders = new HashMap<>();
        Order order =  getAllOrdersWithStatus(status).stream()
                .min((order1, order2) -> order1.timestamp().compareTo(order2.timestamp()))
                .orElseThrow(() -> new NoSuchElementException("No order found"));
        orders.put(order.status(), order);
        return orders;
    }
}
