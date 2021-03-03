package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class purchaseSteps {

    WebDriver driver;
    WebElement element;
    private int amountExpected;
    private int purchase;
    private String purchaseId;

    @Given("^Accessing Demo Online Shop$")
    public void accessing_demo_online_shop() {
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/index.html");
        driver.manage().window().maximize();
    }

    @And("Navigate to {string} and AddToCart")
    public void navigateToAndAddToCart(String product1) {
        //Wait until page loads
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element= wait.until(ExpectedConditions.elementToBeClickable(By.linkText(product1)));

        //Access product details
        driver.findElement(By.linkText(product1)).click();
        element= wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Add")));

        //Add product to cart
        driver.findElement(By.partialLinkText("Add")).click();
        wait.until(ExpectedConditions.alertIsPresent());

        //Manage Popup Confirmation
        Alert alert = driver.switchTo().alert();
        assertTrue(alert.getText().equalsIgnoreCase("Product added"));
        alert.accept();

        //Return to Home Page
        driver.findElement(By.xpath("//*[@id=\"navbarExample\"]/ul/li[1]/a")).click();
    }

    @And("Navigate to Laptop {string} and AddToCart")
    public void navigateToLaptopAndAddToCart(String product2) {
        //Wait until page loads
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element= wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Laptops")));

        //Access Laptops Menu
        driver.findElement(By.linkText("Laptops")).click();
        element= wait.until(ExpectedConditions.elementToBeClickable(By.linkText(product2)));

        //Access product details
        driver.findElement(By.linkText(product2)).click();
        element= wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Add")));

        //Add product to cart
        driver.findElement(By.partialLinkText("Add")).click();
        wait.until(ExpectedConditions.alertIsPresent());

        //Manage Popup Confirmation
        Alert alert = driver.switchTo().alert();
        assertTrue(alert.getText().equalsIgnoreCase("Product added"));
        alert.accept();

        //Return to Home Page
        driver.findElement(By.xpath("//*[@id=\"navbarExample\"]/ul/li[1]/a")).click();
    }

    @And("Navigate to Cart and delete {string} Laptop")
    public void navigate_to_cart_and_delete_laptop_dell(String product) {
        //Wait until page loads
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cartur\"]")));

        //Navigate to Cart
        driver.findElement(By.xpath("//*[@id=\"cartur\"]")).click();
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("table-responsive")));

        //Capturing Amount expected
        element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("panel-title")));
        String amountE = driver.findElement(By.className("panel-title")).getAttribute("innerHTML");
        this.amountExpected = Integer.parseInt(amountE);
    }

    @When("Click on Place Order")
    public void click_on_place_order() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        //Click on Place order
        element= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Place Order')]")));
        assertTrue(element.isEnabled());
        WebElement button = driver.findElement(By.xpath("//button[contains(text(), 'Place Order')]"));

        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript("arguments[0].click();", button);
    }

    @And("Fill in all web form fields:")
    public void fill_in_all_web_form_fields(DataTable table) {
        //Initializing the Form variables
        String name = null;
        String country = null;
        String city = null;
        String creditCard = null;
        String month = null;
        String year = null;
        List<List<String>> rows = table.asLists(String.class);
        List<List<String>> rowsNoHeads = rows.subList(1, rows.size());

        //Catching values
        for(List<String> row:rowsNoHeads){
            name = row.get(0);
            country = row.get(1);
            city = row.get(2);
            creditCard = row.get(3);
            month = row.get(4);
            year = row.get(5);

            System.out.println("The values captured are: Name -> " + name + " | Country -> " + country + " | City -> " + city + " | Credit Card -> " + creditCard + " | Month -> " + month + " | Year -> " + year);
        }

        //Wait until page loads
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModalLabel")));

        //Set values into the form
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("country")).sendKeys(country);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("card")).sendKeys(creditCard);
        driver.findElement(By.id("month")).sendKeys(month);
        driver.findElement(By.id("year")).sendKeys(year);
    }

    @And("Click on purchase")
    public void click_on_purchase() {

        WebElement button = driver.findElement(By.xpath("//button[contains(text(), 'Purchase')]"));
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript("arguments[0].click();", button);
    }

    @Then("Capture and Log purchase Id and Amount")
    public void capture_and_log_purchase_id_and_amount() {
        //Wait until purchase confirmation
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Thank you for your purchase!')]")));
        String text = driver.findElement(By.className("text-muted")).getText();

        //Separating the text content
        String[] splitA = text.split("Amount:");
        String idArray = splitA[0];
        String amountArray = splitA[1];
        String[] splitB = idArray.split(":");
        String[] splitC = amountArray.split("USD");
        String amount = splitC[0].trim();

        this.purchaseId = splitB[1];
        this.purchase = Integer.parseInt(amount);

        System.out.println("The purchase Id is: " + this.purchaseId);
        System.out.println("The purchase Amount is: " + this.purchase);
    }

    @And("Assert purchase amount equals expected")
    public void assert_purchase_amount_equals_expected() {
        Boolean results = false;
        if(this.amountExpected == this.purchase){
            results = true;
            System.out.println("The expected Amount is printed");
        }
        Assert.assertTrue(results);
    }

    @And("Click on OK")
    public void click_on_ok() {

        //Introducing a delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Click on Button OK
        WebElement button = driver.findElement(By.xpath("//button[contains(text(), 'OK')]"));
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript("arguments[0].click();", button);
    }

}
