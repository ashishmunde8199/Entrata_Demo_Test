package Usage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {

    // Declare WebDriver instance
    private WebDriver driver;

    // Method to launch Chrome and navigate to a URL
    public void ChromeLaunch() {
        String projectPath = System.getProperty("user.dir");

        // Set the ChromeDriver path relative to the project directory
        String driverPath = projectPath + "D:/Automation/Browser";

        // Set the system property for ChromeDriver
        System.setProperty("webdriver.edge.driver", driverPath);

        // Initialize the ChromeDriver
        driver = new ChromeDriver();

        // Open the URL in Chrome
        driver.get("https://www.entrata.com/");
        driver.manage().window().maximize();
    }

    // Method to close Chrome browser
    public void closeChrome() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Method to return the WebDriver instance
    public WebDriver getDriver() {
        return this.driver;
    }
}