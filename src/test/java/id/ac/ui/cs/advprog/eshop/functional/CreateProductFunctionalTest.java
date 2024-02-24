package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// This class conducts end-to-end testing of the product addition feature, ensuring the web environment is booted on an arbitrary port.
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)

public class CreateProductFunctionalTest {

    // This variable holds the actual port number utilized during the test.
    @LocalServerPort
    private int testingPort;

    // Default base URL pointing to localhost, which can be overridden by configuration settings.
    @Value("${app.baseUrl:http://localhost}")
    private String baseUrl;

    // Full URL that points to the page where products are listed, constructed before each test.
    private String fullProductListUrl;
    private String createProductUrl;

    @BeforeEach
    public void initializeTestSetup() {
        // Construct the URL to access the product list and display it in the console for debugging.
        fullProductListUrl = String.format("%s:%d/product/list", baseUrl, testingPort);
        createProductUrl = baseUrl + ":" + testingPort + "/product/create";

    }

    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(fullProductListUrl); // This now accesses http://localhost:<port>/product
        String pageTitle = driver.getTitle();
        //Verify
        assertEquals("Product List", pageTitle);
    }

    @Test
    void welcomeMessage_productList_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise
        driver.get(fullProductListUrl);
        String welcomeMessage = driver.findElement(By.tagName("h3" )).getText();
        // Verify
        assertEquals("Welcome", welcomeMessage);
    }

     //Test method to verify that the product creation workflow operates as expected.
    @Test
    void validate_createButton_on_productListPage(ChromeDriver driver) throws Exception {
        // Opens the product listing URL in the browser.
        driver.get(fullProductListUrl);
        //disini pakai a bukan button, krn di html productList button create product itu diwrap dengan elemen anchor yang disesuaikan sbg button
        WebElement createButton = driver.findElement(By.xpath("//a[contains(text(), 'Create Product')]"));
        createButton.click();

        String currentUrl = driver.getCurrentUrl();

        // Validate that the current URL matches the expected URL.
        assertEquals(createProductUrl, currentUrl);
    }

    @Test
    void validate_createProductPage(ChromeDriver driver) throws Exception {
        driver.get(createProductUrl);

        // Fill in the product creation form
        WebElement productNameField = driver.findElement(By.name("productName"));
        productNameField.clear();
        String productName = "Tes Nama Produk";
        productNameField.sendKeys(productName);

        WebElement productQuantityField = driver.findElement(By.name("productQuantity"));
        productQuantityField.clear();
        String productQuantity = "100";
        productQuantityField.sendKeys(productQuantity);

        // Submit the form
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        // Navigate to the product list page
        driver.get(fullProductListUrl);

        // Validate the product exists in the list
        boolean isProductFound = driver.findElements(By.xpath("//*[contains(text(), '" + productName + "')]")).size() > 0;
        boolean isQuantityFound = driver.findElements(By.xpath("//*[contains(text(), '" + productQuantity + "')]")).size() > 0;

        assertTrue(isProductFound, "Product name not found in the list.");
        assertTrue(isQuantityFound, "Product quantity not found in the list.");
    }

    // Utility method to create a product
    public void createProduct(ChromeDriver driver, String name, int quantity) {
        driver.get(createProductUrl);

        WebElement productNameField = driver.findElement(By.name("productName"));
        productNameField.clear();
        productNameField.sendKeys(name);

        WebElement productQuantityField = driver.findElement(By.name("productQuantity"));
        productQuantityField.clear();
        productQuantityField.sendKeys(String.valueOf(quantity));

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        // Navigate to the product list page
        driver.get(fullProductListUrl);
    }

    @Test
    void testDeleteProduct(ChromeDriver driver) throws Exception {
        String uniqueProductName = "Delete Temporary Product";
        createProduct(driver, uniqueProductName, 10);

        driver.get(fullProductListUrl);

        WebElement deleteLink = driver.findElement(By.xpath("//td[contains(text(), '" + uniqueProductName + "')]/../td/a[contains(text(), 'Delete')]"));
        deleteLink.click();

        //di sini ngehandle alert "Are you sure you want to delete this product?"
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            // Handle cases where no confirmation is needed
        }

        int productCount = driver.findElements(By.xpath("//td[contains(text(), '" + uniqueProductName + "')]")).size();

        assertEquals(0, productCount, "Product should no longer exist after deletion.");
    }

    @Test
    void testEditProduct (ChromeDriver driver) throws Exception {
        driver.get(fullProductListUrl);
        WebElement editLink = driver.findElement(By.xpath("//td[contains(text(), 'Tes Nama Produk')]/../td/a[contains(text(), 'Edit')]"));
        editLink.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/edit/"), "Not on the expected product edit page.");

        WebElement productNameField = driver.findElement(By.name("productName"));
        productNameField.clear();
        String productName = "Edit Nama Produk";
        productNameField.sendKeys(productName);

        WebElement productQuantityField = driver.findElement(By.name("productQuantity"));
        productQuantityField.clear();
        String productQuantity = "10";
        productQuantityField.sendKeys(productQuantity);

        WebElement editButton = driver.findElement(By.xpath("//button[@type='submit']"));
        editButton.click();

        String currentUrlFinal= driver.getCurrentUrl();
        assertEquals(fullProductListUrl, currentUrlFinal);

        // Validate the product exists in the list
        boolean isProductFound = driver.findElements(By.xpath("//*[contains(text(), '" + productName + "')]")).size() > 0;
        boolean isQuantityFound = driver.findElements(By.xpath("//*[contains(text(), '" + productQuantity + "')]")).size() > 0;

        assertTrue(isProductFound, "Product name not found in the list.");
        assertTrue(isQuantityFound, "Product quantity not found in the list.");

    }

}
