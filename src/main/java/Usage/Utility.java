package Usage;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utility {
    private final WebDriver driver;

    public Utility(WebDriver driver) {
        this.driver = driver;
    }

    public boolean ValidateLink(By locator) {
        try {
            // Find all the elements specified by the locator
            List<WebElement> links = driver.findElements(locator);
            int workingLinksCount = 0;
            int brokenLinksCount = 0;

            // Loop through each link and validate the URL
            for (WebElement link : links) {
                String url = link.getAttribute("href");

                if (validateLink(url)) {
                    workingLinksCount++;
                } else {
                    brokenLinksCount++;
                }
            }

            // Print summary of results
            printSummary(links.size(), workingLinksCount, brokenLinksCount);
        } catch (NoSuchElementException exception) {
            System.out.println("Element not found: " + exception.getMessage());
            exception.printStackTrace();
        } catch (TimeoutException exception) {
            System.out.println("Timeout occurred: " + exception.getMessage());
            exception.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Exception occurred: " + exception.getMessage());
            exception.printStackTrace();
        }
        return true;
    }

    // Method to print the summary of link validation results
    private void printSummary(int totalLinks, int workingLinks, int brokenLinks) {
        System.out.println("Summary:");
        System.out.println("Total Links: " + totalLinks);
        System.out.println("Working Links: " + workingLinks);
        System.out.println("Broken Links: " + brokenLinks);

        if (brokenLinks > 0) {
            System.out.println("Some links are broken. Please check the logs for details.");
        } else {
            System.out.println("All links are working fine.");
        }
    }

    // Method to check if a link is valid or broken
    private boolean validateLink(String linkUrl) {
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();

            // Check if the response code is 200 (OK)
            if (responseCode >= 200 && responseCode < 400) {
                System.out.println(linkUrl + " is working. Response Code: " + responseCode);
                return true;
            } else {
                System.out.println(linkUrl + " is broken. Response Code: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            System.out.println(linkUrl + " is a malformed or inaccessible URL.");
            return false;
        }
    }
}