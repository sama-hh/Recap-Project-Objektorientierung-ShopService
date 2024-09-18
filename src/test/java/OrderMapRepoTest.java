import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel", 20);
        Instant timestamp = Instant.now();
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING, timestamp);
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel", 20);
        expected.add(new Order("1", List.of(product1), OrderStatus.PROCESSING, timestamp));

        assertEquals(expected, actual);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel", 20);
        Instant timestamp = Instant.now();
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING, timestamp);
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product product1 = new Product("1", "Apfel", 20);
        Order expected = new Order("1", List.of(product1), OrderStatus.PROCESSING, timestamp);

        assertEquals(expected, actual);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel", 20);
        Instant timestamp = Instant.now();
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING, timestamp);
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel", 20);
        Order expected = new Order("1", List.of(product1), OrderStatus.PROCESSING, timestamp);
        assertEquals(expected, actual);
        assertEquals(expected, repo.getOrderById("1"));
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }
}
