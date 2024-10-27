package Usage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private  final WebDriver driver;
    private  final Utility utility;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.utility = new Utility(driver); // Instantiate GenericFunctionality
    }

    public void AcceptCookies() {
        WebElement acceptCookies = driver.findElement(By.xpath("//a[@id='cookie-accept']"));
        acceptCookies.click();
    }

    public void ValidateFooterLinks() {
        WebElement footer = driver.findElement(By.xpath("//div[@class='footer-nav']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", footer);

        By footerLinksLocator = By.xpath("//div[@class='footer-nav']//a");

        // Call the ValidateLink method from Utility
        boolean result = utility.ValidateLink(footerLinksLocator);
        if (result) {
            System.out.println("All footer links have been validated.");
        } else {
            System.out.println("Some footer links could not be validated.");
        }
    }

    public void WindowHandling() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions action = new Actions(driver);

        // Click on the 'Summit' link to open the Summit page in a new tab
        WebElement summitLink = driver.findElement(By.xpath("//a[contains(text(),'Summit')]"));
        action.moveToElement(summitLink).click().perform();

        // Wait for the new Summit tab to open
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Store the current window handle (home page)
        String originalWindow = driver.getWindowHandle();

        // Switch to the new Summit tab
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        for (String window : windows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window); // Switch to Summit tab
                System.out.println("Switched to Summit page.");
                break;
            }
        }
    }

    // Method for clicking Register button and validating the form
    public void ValidateRegisterFormMandatoryField() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        Actions action = new Actions(driver);

        // Click on the 'Summit' link to open the Summit page in a new tab
        WebElement summitLink = driver.findElement(By.xpath("//a[contains(text(),'Summit')]"));
        action.moveToElement(summitLink).click().perform();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Step 4: Store the current window handle (parent)
        String parentWindow = driver.getWindowHandle();

        // Step 5: Switch to the new child window
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        for (String window : windows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window); // Switch to child window
                System.out.println("Switched to Child Window.");
                break;
            }
        }

        // Click on the 'Register' button on the Summit page
        WebElement registerButton = driver.findElement(By.xpath("//a[text()='Register Now']"));
        registerButton.click();

        // Wait for the Register tab to open
        wait.until(ExpectedConditions.numberOfWindowsToBe(3));

        // Step 8: Switch to the new grandchild window
        String childWindow = driver.getWindowHandle(); // Store current (child) window
        windows = new ArrayList<>(driver.getWindowHandles());
        for (String window : windows) {
            if (!window.equals(parentWindow) && !window.equals(childWindow)) {
                driver.switchTo().window(window); // Switch to grandchild window
                System.out.println("Switched to Grandchild Window.");
                break;
            }
        }

        // Step: Verify that the "Registration Items" header is present on the Register page
        WebElement registrationHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Registration Items']")));
        String registerHeaderText = registrationHeader.getText();
        //Assert.assertEquals(registerHeaderText, "Registration Items");
        System.out.println("Successfully navigated to the Register page.");

        // Step: Click 'Next' button without filling any details
        WebElement nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='forward']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", nextButton);

        // Click the 'Next' button
        action.moveToElement(nextButton).click().perform();

        // Check if the "First Name" text box is highlighted
        By firstNameLocator = By.xpath("//input[@aria-label='First Name']");
        By errorMessageLocator = By.xpath("(//div[@data-cvent-id='error-messages']//div)[1]");

        String strFromColor = driver.findElement(firstNameLocator).getCssValue("color");
        String strFromBackColor = driver.findElement(firstNameLocator).getCssValue("background-color");

        System.out.println("Color: " + strFromColor);
        System.out.println("Background Color: " + strFromBackColor);

        if (!strFromColor.equals(strFromBackColor)) {
            System.out.println("Text is highlighted");
        } else {
            System.out.println("Text is not highlighted");
        }

        // Validate error message
        String errorMessage = driver.findElement(errorMessageLocator).getText();
       // Assert.assertEquals(errorMessage, "First Name is required.");
    }
}