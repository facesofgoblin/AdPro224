package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product create(Product product) {
        // Trim the product name to ensure validation checks are against meaningful input
        String productName = product.getProductName() != null ? product.getProductName().trim() : null;

        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        } else if (!productName.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Product name can only contain letters and spaces.");
        }

        if (product.getProductQuantity() < 1) {
            throw new IllegalArgumentException("Product quantity must be a positive number.");
        }

        return productRepository.create(product);
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }

    public Product getProduct(String id){
        return productRepository.findProductById(id);
    }

    @Override
    public Product update(Product product) {
        productRepository.update(product);
        return product;
    }

    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
}

