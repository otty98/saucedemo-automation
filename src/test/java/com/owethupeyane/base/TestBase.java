package com.owethupeyane.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.time.Duration;

/**
 * This class serves as a base class for all test classes.
 * It handles setup and teardown of the WebDriver.
 * Also captures a screenshot if a test fails and attaches it to the Allure report.
 */
public class TestBase {

    // WebDriver instance shared by all test classes that extend this class
    protected WebDriver driver;

    /**
     * This method runs before each test method.
     * It sets up the Chrome browser using WebDriverManager, opens the browser,
     * maximizes the window, sets implicit wait, and navigates to the SauceDemo site.
     */
    @BeforeMethod
    public void setUp() {
        // Automatically manages the correct ChromeDriver version
        WebDriverManager.chromedriver().setup();

        // Launches a new Chrome browser instance
        driver = new ChromeDriver();

        // Maximizes the browser window
        driver.manage().window().maximize();

        // Sets implicit wait for finding elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Opens the target website before each test
        driver.get("https://www.saucedemo.com");
    }

    /**
     * This method runs after each test method.
     * If a test fails, it captures a screenshot and attaches it to the Allure report.
     * It also quits the browser.
     *
     * @param result The result of the test (used to detect failure).
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        // If test fails, take a screenshot
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }

        // Quit the browser and end the WebDriver session
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Captures a screenshot and attaches it to the Allure report.
     *
     * @param testName The name of the failed test for labeling the screenshot.
     */
    private void takeScreenshot(String testName) {
        // Takes a screenshot as bytes
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attaches the screenshot to the Allure report
        Allure.addAttachment(testName, new ByteArrayInputStream(screenshot));
    }
}
