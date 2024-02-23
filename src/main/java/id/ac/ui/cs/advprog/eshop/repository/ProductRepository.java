
package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
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

        //ngecek kalau id itu -1 atau engga, kalau -1 berarti product nya gaada
        if (existingProductId == -1){
            throw new IllegalArgumentException("Product with ID " + product.getProductId() + " doesn't exist.");
        }
        productData.set(existingProductId, product);
        return product;
    }

    public void delete(String id) {
        boolean found = false;
        Iterator<Product> iterator = productData.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(id)) {
                iterator.remove();
                found = true;
                break;
            }
        } // kasus kalau id nya ga ditemukan berarti product nya gaada
        if (!found) {
            throw new IllegalArgumentException("Nothing to delete :)");
        }
    }
    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}