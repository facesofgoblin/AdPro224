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
    //Deleting variables emptyNameError and specialCharNameError because it's not really used often in other functions beside the create method
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product create(Product product) {
        // di sini seharusnya belum memanggil productRepository.create(product)
        // validasi data terlebih dahulu, sebelum membuat produk
        if (product.getProductName() == null){
            throw new IllegalArgumentException("Product name cannot be empty.");
        } else if (!product.getProductName().matches("[A-Za-z ]+")){
            throw new IllegalArgumentException("Special characters are not allowed!");
        }
        if (product.getProductQuantity() < 1){
            throw new IllegalArgumentException("Invalid quantity!");
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

