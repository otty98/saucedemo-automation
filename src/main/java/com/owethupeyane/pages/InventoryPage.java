package com.owethupeyane.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By inventoryItems = By.className("inventory_item");
    private final By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private final By removeFromCartButtons = By.xpath("//button[contains(text(),'Remove')]");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Get the total number of products displayed on the inventory page
     */
    public int getProductCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItems));
        return driver.findElements(inventoryItems).size();
    }

    /**
     * Add the first item to cart
     */
    public void addFirstItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons));
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            // Wait for the button text to change or cart badge to appear
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(cartBadge),
                    ExpectedConditions.presenceOfElementLocated(removeFromCartButtons)
            ));
        }
    }

    /**
     * Add item to cart by product name (used in your tests)
     */
    public void addItemToCartByName(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(buttonId)));
        addButton.click();

        // Wait for the action to complete
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(cartBadge),
                ExpectedConditions.presenceOfElementLocated(removeFromCartButtons)
        ));
    }

    /**
     * Add multiple items to cart by index (used in your tests)
     */
    public void addMultipleItemsToCart(int... itemIndices) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));
        List<WebElement> buttons = driver.findElements(addToCartButtons);

        for (int index : itemIndices) {
            if (index >= 0 && index < buttons.size()) {
                buttons.get(index).click();
                // Small wait between clicks to avoid rapid clicking issues
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Wait for all items to be added
        wait.until(ExpectedConditions.presenceOfElementLocated(cartBadge));
    }

    /**
     * Get the current cart item count from the badge
     */
    public int getCartItemCount() {
        try {
            WebElement badge = wait.until(ExpectedConditions.presenceOfElementLocated(cartBadge));
            return Integer.parseInt(badge.getText());
        } catch (Exception e) {
            return 0; // No badge means empty cart
        }
    }

    /**
     * Open the shopping cart page (if you need it later)
     */
    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        driver.findElement(cartLink).click();
    }
}