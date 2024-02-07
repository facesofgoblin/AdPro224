package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

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
    private String baseWebUrl;

    // Full URL that points to the page where products are listed, constructed before each test.
    private String fullProductListUrl;

    @BeforeEach
    public void initializeTestSetup() {
        // Construct the URL to access the product list and display it in the console for debugging.
        fullProductListUrl = String.format("%s:%d/product/list", baseWebUrl, testingPort);
        System.out.println("Full Product Listing URL: " + fullProductListUrl);
    }

    // Test method to verify that the product creation workflow operates as expected.
    @Test
    public void validateProductCreationWorkflow(ChromeDriver driver) throws Exception {
        // Opens the product listing URL in the browser.
        driver.get(fullProductListUrl);

        // Triggers the product addition process.
        WebElement addButton = driver.findElement(By.name("create-button"));
        addButton.click();

        // Prepares the form for a new product entry.
        WebElement productNameField = driver.findElement(By.id("nameInput"));
        productNameField.clear();

        WebElement productQuantityField = driver.findElement(By.id("quantityInput"));
        productQuantityField.clear();

        // Inputs the new product's name and quantity.
        String productName = "Shampoo cap Bambang";
        productNameField.sendKeys(productName);

        String productQuantity = "1";
        productQuantityField.sendKeys(productQuantity);

        // Confirms the product addition.
        WebElement confirmButton = driver.findElement(By.tagName("button"));
        confirmButton.click();

        // Validates that the new product details appear on the resulting page.
        String resultingPageContent = driver.getPageSource();
        assertTrue(resultingPageContent.contains(productName));
        assertTrue(resultingPageContent.contains(productQuantity));
    }
}
