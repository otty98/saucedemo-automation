package com.owethupeyane.tests;


import com.owethupeyane.base.TestBase;
import com.owethupeyane.pages.InventoryPage;
import com.owethupeyane.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Inventory Management")
@Feature("Product Display")
public class InventoryTests extends TestBase {

    @DataProvider(name = "users")
    public Object[][] getUsers() {
        return new Object[][] {
                {"standard_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"}
        };
    }


    @Test(dataProvider = "users")
    @Story("Verify product count for different users")
    @Description("Ensure all users see 6 products")
    public void verifyProductCount(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertEquals(inventoryPage.getProductCount(), 6);
    }

    @Test
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddToCart() {
        new LoginPage(driver).login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1);
    }

    @Test
    public void verifyProductCount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // Pass driver to InventoryPage constructor
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertEquals(inventoryPage.getProductCount(), 6);
    }
}

