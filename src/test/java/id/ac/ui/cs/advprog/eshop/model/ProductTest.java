package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;
    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals( 100, this.product.getProductQuantity());
    }

    // Testing untuk set nama produk dan kuantitas produk
    @Test
    void testSetProductName() {
        String newName = "Sampo Cap Baru";
        product.setProductName(newName);
        assertEquals(newName, product.getProductName());
    }
    @Test
    void testSetProductQuantity() {
        int newQuantity = 150;
        product.setProductQuantity(newQuantity);
        assertEquals(newQuantity, product.getProductQuantity());
    }

}



