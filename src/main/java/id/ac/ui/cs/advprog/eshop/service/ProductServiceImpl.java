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

    public String emptyNameError = "Name shouldn't be empty!";
    public String specialCharNameError = "Special characters are not allowed!";
    @Override
    public Product create(Product product) {
        productRepository.create(product);

        if (product.getProductName() == null){
            throw new IllegalArgumentException(emptyNameError);
        } else if (!product.getProductName().matches("[A-Za-z ]+")){
            throw new IllegalArgumentException(specialCharNameError);
        }

        if (product.getProductQuantity() < 1){
            throw new IllegalArgumentException("Invalid quantity!");
        }
        return product;
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

