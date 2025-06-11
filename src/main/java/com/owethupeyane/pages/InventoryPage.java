package com.owethupeyane.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By inventoryItems = By.className("inventory_item");
    private final By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int getProductCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItems));
        return driver.findElements(inventoryItems).size();
    }

    public void addFirstItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons));
        driver.findElements(addToCartButtons).get(0).click();
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(driver.findElement(cartBadge).getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void openCart() {
        driver.findElement(cartLink).click();
    }

    public List<String> getAllProductNames() {
        return driver.findElements(inventoryItems).stream()
                .map(item -> item.findElement(By.className("inventory_item_name")).getText())
                .collect(Collectors.toList());
    }
}
