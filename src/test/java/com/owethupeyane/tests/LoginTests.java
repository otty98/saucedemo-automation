package com.owethupeyane.tests;

import com.owethupeyane.base.TestBase;
import com.owethupeyane.pages.LoginPage;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class contains automated test cases related to login functionality
 * for the SauceDemo website. It extends the TestBase class to reuse
 * browser setup and teardown logic.
 */
public class LoginTests extends TestBase {

    /**
     * Test to verify a successful login using valid credentials.
     * Uses the LoginPage object to interact with the login form.
     * Asserts that the current URL contains "inventory.html" after logging in.
     */
    @Test
    @Step("Verify valid login with correct credentials")
    public void successfulLogin() {
        // Create an instance of the LoginPage and pass the WebDriver
        LoginPage loginPage = new LoginPage(driver);

        // Perform login with valid credentials
        loginPage.login("standard_user", "secret_sauce");

        // Verify that the user is redirected to the inventory page
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    /**
     * Test to verify that a locked-out user sees the correct error message.
     * Uses the LoginPage object to perform the login.
     * Asserts that the error message is exactly as expected.
     */
    @Test
    @Step("Verify login fails with wrong password")
    public void lockedOutUser() {
        // Create an instance of the LoginPage and pass the WebDriver
        LoginPage loginPage = new LoginPage(driver);

        // Attempt to log in with a locked-out user
        loginPage.login("locked_out_user", "secret_sauce");

        // Verify that the correct error message is shown
        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out.");
    }
}
