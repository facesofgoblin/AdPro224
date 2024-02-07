package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create (Product product){
        productData.add(product);
        return product;
    }
    public Product findProductById(String id) {
        for (Product product : productData) {
            if (Objects.equals(product.getProductId(), id)) {
                return product;
            }
        }
        return null;
    }

    public Product update (Product product){
        int existingProductId = productData.indexOf(findProductById(product.getProductId()));
        productData.set(existingProductId, product);
        return product;
    }
    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}



