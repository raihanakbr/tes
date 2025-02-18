package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();
    private int testsws;

    public Product create(Product product) {
        if (product.getProductId() == null) {
            UUID uuid = UUID.randomUUID();
            product.setProductId(uuid.toString());
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        for (Product product : productData)
            if (product.getProductId().equals(productId))
                return product;
        throw new NoSuchElementException("Product not found with ID: " + productId);
    }

    public Product edit(Product editedProduct) {
        Product product = findById(editedProduct.getProductId());
        product.setProductName(editedProduct.getProductName());
        product.setProductQuantity(editedProduct.getProductQuantity());
        return product;
    }

    public Product deleteById(String productId) {
        Product productToDelete = findById(productId);
        productData.remove(productToDelete);
        return productToDelete;
    }
}
