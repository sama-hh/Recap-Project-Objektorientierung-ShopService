import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @org.junit.jupiter.api.Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel",20));

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel",20));
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.addProduct(new Product("1", "Apfel",20));

        //WHEN
        Optional<Product> actual = repo.getProductById("1");

        //THEN
        Product expected = new Product("1", "Apfel",20);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @org.junit.jupiter.api.Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product newProduct = new Product("2", "Banane",10);

        //WHEN
        repo.addProduct(newProduct);
        Optional<Product> actual = repo.getProductById("2");
        //THEN
        Product expected = new Product("2", "Banane",10);
        assertEquals(expected, actual.get());
    }

    @org.junit.jupiter.api.Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        Product product= new Product("1", "Apfel",20);
        repo.addProduct(product);
        //WHEN
        repo.removeProduct("1");
        Optional<Product> removedProduct = repo.getProductById("1");
        //THEN
        assertFalse(removedProduct.isPresent());
    }
}
