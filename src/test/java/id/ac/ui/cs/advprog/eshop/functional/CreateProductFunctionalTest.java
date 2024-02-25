package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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


    @Test
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

    void clickElement(WebDriver driver, By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    void fillTextField(WebDriver driver, By locator, String text) {
        WebElement textField = driver.findElement(locator);
        textField.clear();
        textField.sendKeys(text);
    }

    void submitForm(WebDriver driver, By submitButtonLocator) {
        clickElement(driver, submitButtonLocator);
    }

    //Test method to verify that the product creation workflow operates as expected.
    @Test
    void validate_createButton_on_productListPage(ChromeDriver driver) throws Exception {
        driver.get(fullProductListUrl);
        clickElement(driver, By.xpath("//a[contains(text(), 'Create Product')]"));
        assertEquals(createProductUrl, driver.getCurrentUrl());
    }


    @Test
    void validate_createProductPage(ChromeDriver driver) throws Exception {
        driver.get(createProductUrl);

        fillTextField(driver, By.name("productName"), "Tes Nama Produk");
        fillTextField(driver, By.name("productQuantity"), "100");

        submitForm(driver, By.xpath("//button[@type='submit']"));
        boolean isProductFound = !driver.findElements(By.xpath("//*[contains(text(), 'Tes Nama Produk')]")).isEmpty();
        assertTrue(isProductFound, "Product name not found in the list.");
    }


    @Test
    void testDeleteProduct(ChromeDriver driver) throws Exception {
        driver.get(createProductUrl);
        String uniqueProductName = "Delete Temporary Product";
        fillTextField(driver, By.name("productName"), uniqueProductName);
        fillTextField(driver, By.name("productQuantity"), "100");
        submitForm(driver, By.xpath("//button[@type='submit']"));

        clickElement(driver, By.xpath("//td[contains(text(), '" + uniqueProductName + "')]/../td/a[contains(text(), 'Delete')]"));

        // Handle alert if present
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) { }

        assertEquals(0, driver.findElements(By.xpath("//td[contains(text(), '" + uniqueProductName + "')]")).size(), "Product should no longer exist after deletion.");
    }

    @Test
    void testEditProduct(ChromeDriver driver) throws Exception {
        driver.get(fullProductListUrl);
        clickElement(driver, By.xpath("//td[contains(text(), 'Tes Nama Produk')]/../td/a[contains(text(), 'Edit')]"));
        assertTrue(driver.getCurrentUrl().contains("/product/edit/"), "Not on the expected product edit page.");

        fillTextField(driver, By.name("productName"), "Edit Nama Produk");
        fillTextField(driver, By.name("productQuantity"), "10");
        submitForm(driver, By.xpath("//button[@type='submit']"));

        assertEquals(fullProductListUrl, driver.getCurrentUrl(), "Not redirected back to the product list page.");
        boolean isProductFound = !driver.findElements(By.xpath("//*[contains(text(), 'Edit Nama Produk')]")).isEmpty();
        assertTrue(isProductFound, "Edited product name not found in the list.");
    }

}
