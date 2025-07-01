package com.owethupeyane.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.HashMap;

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
        WebDriverManager.chromedriver().setup();

        // Disable Chrome password manager popups and automation warnings
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("prefs", new HashMap<String, Object>() {{
            put("credentials_enable_service", false);
            put("profile.password_manager_enabled", false);
        }});

        // Launch Chrome with the custom options
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
