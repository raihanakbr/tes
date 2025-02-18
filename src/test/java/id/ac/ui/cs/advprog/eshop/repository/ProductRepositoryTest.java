package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af61af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af61af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af61af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af61af63bd6");
        editedProduct.setProductName("New Product Name");
        editedProduct.setProductQuantity(50);
        productRepository.edit(editedProduct);

        Product savedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af61af63bd6");

        assertEquals("New Product Name", savedProduct.getProductName());
        assertEquals(50, savedProduct.getProductQuantity());
    }

    @Test
    void testEditNonExistentProduct() {
        Product editedProduct = new Product();
        editedProduct.setProductId("non-existent-id");
        editedProduct.setProductName("Edited Name");
        editedProduct.setProductQuantity(10);

        assertThrows(NoSuchElementException.class, () -> {
            productRepository.edit(editedProduct);
        });
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("3f90c1db-afea-4a03-8dae-66f40e06f715");
        product.setProductName("Sampo Cap Agus");
        product.setProductQuantity(69);
        productRepository.create(product);

        Product deletedProduct = productRepository.deleteById("3f90c1db-afea-4a03-8dae-66f40e06f715");

        // Verify that the deleted product is returned correctly
        assertNotNull(deletedProduct);
        assertEquals("3f90c1db-afea-4a03-8dae-66f40e06f715", deletedProduct.getProductId());

        assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById("3f90c1db-afea-4a03-8dae-66f40e06f715");
        });
    }

    @Test
    void testDeleteNonExistentProduct() {
        assertThrows(NoSuchElementException.class, () -> {
            productRepository.deleteById("non-existent-id");
        });
    }

    @Test
    void testDeleteFromEmptyRepository() {
        assertThrows(NoSuchElementException.class, () -> {
            productRepository.deleteById("eb558e9f-1c39-460e-8860-71af61af63bd6");
        });
    }

    @Test
    void testFindByIdFound() {
        Product product = new Product();
        product.setProductId("3f90c1db-afea-4a03-8dae-66f40e06f715");
        product.setProductName("Soap By Kadal");
        product.setProductQuantity(420);
        productRepository.create(product);

        Product findProduct = productRepository.findById("3f90c1db-afea-4a03-8dae-66f40e06f715");
        assertEquals(product, findProduct);
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId("3f90c1db-afea-4a03-8dae-66f40e06f715");
        product.setProductName("Soap By Kadal");
        product.setProductQuantity(420);
        productRepository.create(product);

        assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById("eb558e9f-1c39-460e-8860-71af61af63bd6");
        });
    }
}