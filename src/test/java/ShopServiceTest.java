import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderRepo = new OrderMapRepo();
        Product product= new Product("1", "Apfel");
        productRepo.addProduct(product);
        ShopService shopService = new ShopService(orderRepo, productRepo);
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNotNull(actual);
        assertNotNull(actual.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();

        List<String> productsIds = List.of("1", "2");

        assertThrows(IllegalArgumentException.class, () -> shopService.addOrder(productsIds));
    }
}
