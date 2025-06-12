package com.owethupeyane.tests;

import com.owethupeyane.base.TestBase;
import com.owethupeyane.pages.InventoryPage;
import com.owethupeyane.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for inventory page functionality
 *
 * <p>This class contains test cases for verifying all inventory-related features including:
 * <ul>
 *   <li>Product listing verification</li>
 *   <li>Single item addition to cart</li>
 *   <li>Multiple items addition to cart</li>
 *   <li>Cart badge counter updates</li>
 *   <li>Cart page navigation</li>
 * </ul>
 *
 * <p>All tests follow the pattern:
 * <ol>
 *   <li>Login with standard user credentials</li>
 *   <li>Perform inventory action</li>
 *   <li>Verify expected results</li>
 * </ol>
 *
 * @author Owethu Peyane
 * @version 1.0
 * @since 2025-06-12
 */
@Epic("Inventory Management")
@Feature("Product Cart Operations")
public class InventoryTests extends TestBase {

    /**
     * Verifies the correct number of products are displayed on inventory page
     *
     * <p><b>Test Steps:</b>
     * <ol>
     *   <li>Login as standard user</li>
     *   <li>Get product count from inventory page</li>
     *   <li>Verify count matches expected value (6)</li>
     * </ol>
     *
     * <p><b>Expected Result:</b>
     * Inventory page should display exactly 6 products
     */
    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Inventory Display")
    @Description("Verify product count on inventory page matches expected value")
    @Step("Verify inventory product count")
    public void testProductCount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        int productCount = inventoryPage.getProductCount();

        Assert.assertEquals(productCount, 6, "Inventory should display 6 products");
    }

    /**
     * Tests adding a single item to cart and verifies cart counter updates
     *
     * <p><b>Test Steps:</b>
     * <ol>
     *   <li>Login as standard user</li>
     *   <li>Record initial cart count</li>
     *   <li>Add first item to cart</li>
     *   <li>Verify cart count increments by 1</li>
     * </ol>
     *
     * <p><b>Expected Result:</b>
     * Cart badge counter should increase by 1 after adding an item
     */
    @Test(priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    @Story("Cart Management")
    @Description("Verify single item addition updates cart count correctly")
    @Step("Test single item addition to cart")
    public void testAddFirstItemToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        int initialCount = inventoryPage.getCartItemCount();
        inventoryPage.addFirstItemToCart();
        int updatedCount = inventoryPage.getCartItemCount();

        Assert.assertEquals(updatedCount, initialCount + 1,
                "Cart count should increment by 1 after adding item");
    }

    /**
     * Tests adding an item to cart by product name
     *
     * <p><b>Test Steps:</b>
     * <ol>
     *   <li>Login as standard user</li>
     *   <li>Record initial cart count</li>
     *   <li>Add 'Sauce Labs Backpack' to cart</li>
     *   <li>Verify cart count increments by 1</li>
     * </ol>
     *
     * <p><b>Expected Result:</b>
     * Cart should update when adding items by specific product name
     */
    @Test(priority = 3)
    @Severity(SeverityLevel.NORMAL)
    @Story("Cart Management")
    @Description("Verify item addition by product name functions correctly")
    @Step("Test adding item by product name")
    public void testAddItemByName() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        int initialCount = inventoryPage.getCartItemCount();
        inventoryPage.addItemToCartByName("Sauce Labs Backpack");
        int updatedCount = inventoryPage.getCartItemCount();

        Assert.assertEquals(updatedCount, initialCount + 1,
                "Cart count should increment by 1 when adding by product name");
    }

    /**
     * Tests adding multiple items to cart in sequence
     *
     * <p><b>Test Steps:</b>
     * <ol>
     *   <li>Login as standard user</li>
     *   <li>Record initial cart count</li>
     *   <li>Add first 3 items to cart</li>
     *   <li>Verify cart count increments by 3</li>
     * </ol>
     *
     * <p><b>Expected Result:</b>
     * Cart should properly track multiple item additions
     */
    @Test(priority = 4)
    @Severity(SeverityLevel.NORMAL)
    @Story("Cart Management")
    @Description("Verify multiple items can be added to cart")
    @Step("Test multiple item additions")
    public void testAddMultipleItems() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        int initialCount = inventoryPage.getCartItemCount();
        inventoryPage.addMultipleItemsToCart(0, 1, 2);
        int updatedCount = inventoryPage.getCartItemCount();

        Assert.assertEquals(updatedCount, initialCount + 3,
                "Cart count should reflect multiple added items");
    }

    /**
     * Comprehensive test of cart badge functionality
     *
     * <p><b>Test Steps:</b>
     * <ol>
     *   <li>Login as standard user</li>
     *   <li>Verify cart starts empty</li>
     *   <li>Add first item and verify count=1</li>
     *   <li>Add second item and verify count=2</li>
     * </ol>
     *
     * <p><b>Expected Result:</b>
     * Cart badge should accurately reflect current item count
     */
    @Test(priority = 5)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Cart UI")
    @Description("Verify cart badge updates correctly after each addition")
    @Step("Test cart badge updates")
    public void testCartBadgeUpdates() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertEquals(inventoryPage.getCartItemCount(), 0,
                "Cart should be empty initially");

        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
                "Should show 1 item after adding");

        inventoryPage.addItemToCartByName("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryPage.getCartItemCount(), 2,
                "Should show 2 items after second addition");
    }

    /**
     * Tests navigation to cart page
     *
     * <p><b>Test Steps:</b>
     * <ol>
     *   <li>Login as standard user</li>
     *   <li>Add an item to cart</li>
     *   <li>Click cart icon</li>
     *   <li>Verify navigation to cart page</li>
     * </ol>
     *
     * <p><b>Expected Result:</b>
     * Should successfully navigate to cart page after clicking cart icon
     */
    @Test(priority = 6)
    @Severity(SeverityLevel.MINOR)
    @Story("Navigation")
    @Description("Verify cart page navigation works after adding items")
    @Step("Test cart page navigation")
    public void testCartNavigation() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addFirstItemToCart();
        inventoryPage.openCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"),
                "Should navigate to cart page");
    }
}