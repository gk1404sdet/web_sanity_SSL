package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class WishlistPage extends BasePage {

    public final JavascriptExecutor js = (JavascriptExecutor) driver;

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    private final By wishlistIcon = By.xpath("//img[@alt=\"wish-list\"]");
    public final By wishlistProducts = By.xpath("//div[@class=\"bg-transparent pr-0\"]");
    private final By wishlistIconPLP = By.xpath("//img[@alt=\"empty_icon\"]");
    private final By removeWishlistIconPLP = By.xpath("//img[@alt=\"filled_icon\"]");
    private final By wishlistIconPDP = By.xpath("//p[contains(text(), 'WISHLIST')]");
    private final By addToBag = By.xpath("//div[contains(text(), 'Add to Bag')]");
    private final By sizeBox = By.xpath("//div[@class='cursor-pointer bg-[#F2F2F2] px-[1.111vw] py-[0.833vw] xxl:px-[16px] xxl:py-[12px] false']");
    private final By sizeChat = By.xpath("//img[@alt='product_card_size_chart']");
    public final By youMight = By.xpath("//span[contains(text(), 'You Might Also ')]");



    public void clickOnWishlistIcon() {
        waitForOverlayToDisappear();
        clickOnElement(wishlistIcon);
    }

    public void clickOnWishlistPDP() {
        clickOnElement(wishlistIconPDP);
    }

    public void fetchProductList(By blockLocator) {
        try {
            waitForOverlayToDisappear();;
            waitForPresenceOfElement(blockLocator);

            List<WebElement> blocks = driver.findElements(blockLocator);
            int count = 0;
            System.out.println("----- Wishlist Product List -----");

            for (WebElement block : blocks) {
                String brand = "";
                String price = "";
                // Try extracting brand
                try {
                    WebElement brandEl = block.findElement(By.xpath("//div[@class=\"px-1 pb-1 pt-4 md:py-3\"]"));
                    brand = brandEl.getText().trim();
                } catch (Exception ignored) {
                }
                // Try extracting price
                try {
                    WebElement priceEl = block.findElement(By.xpath(".//div[contains(text(),'₹')]"));
                    price = priceEl.getText().trim();
                } catch (Exception ignored) {
                }

                if (!brand.isEmpty() && !price.isEmpty()) {
                    count++;
                }
            }
            System.out.println("Total products found: " + count);
        } catch (Exception e) {
            Assert.fail("Error extracting brand and price: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addProductsToWishlist(int count) {
        waitForOverlayToDisappear();
        waitForPresenceOfElement(wishlistIconPLP);
        List<WebElement> wishlistIcons = driver.findElements(wishlistIconPLP);
        int added = 0;

        for (WebElement icon : wishlistIcons) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", icon);
                // Wait for overlay to disappear (if present)
                try {
                    By overlay = By.cssSelector("div.fixed.inset-0.z-\\[102\\]");
                    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    shortWait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
                } catch (Exception ignored) {
                }
                // Click to add to wishlist
                wait.until(ExpectedConditions.elementToBeClickable(icon)).click();
                added++;
                System.out.println("Added product " + added + " to wishlist");

                if (added == count) break;

            } catch (Exception e) {
                Assert.fail("Error Adding to wishlist: " + e.getMessage());
            }
        }
        System.out.println("Total added to wishlist: " + added);
    }

    public void removeProductsFromWishlist(int count) {
        waitForOverlayToDisappear();
        List<WebElement> wishlistIcons = driver.findElements(removeWishlistIconPLP);
        int removed = 0;

        for (WebElement icon : wishlistIcons) {
            try {
                String altAttr = icon.getAttribute("alt");
                if ("filled_icon".equalsIgnoreCase(altAttr)) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", icon);
                    try {
                        By overlay = By.cssSelector("div.fixed.inset-0.bg-neutral-900\\/90");
                        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                        shortWait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
                    } catch (Exception ignored) {
                    }
                    // Click to remove
                    new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(icon)).click();
                    removed++;
                    System.out.println("Product removed from wishlist");

                    if (removed == count) break;
                }
            } catch (Exception e) {
                Assert.fail("Error removing from wishlist: " + e.getMessage());
            }
        }
        if (removed > 0) {
            System.out.println("Total removed from wishlist: " + removed);
        } else {
            System.out.println("No products removed. All were already not wishlisted");
        }
    }

    public void clickAddToBagByIndex(int index) {
        try {
            waitForOverlayToDisappear();
            waitForPresenceOfElement(addToBag);
            List<WebElement> addToBagButtons = driver.findElements(addToBag);

            if (addToBagButtons.isEmpty()) {
                System.out.println("No 'Add to Bag' buttons found on the page");
                return;
            }
            if (index >= 0 && index < addToBagButtons.size()) {
                WebElement button = addToBagButtons.get(index);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(button)).click();
                Thread.sleep(2000);
                System.out.println("Clicked 'Add to Bag' at index: " + index);
            } else {
                Assert.fail("Invalid index. Please provide between 0 and " + (addToBagButtons.size() - 1));
            }
        } catch (Exception e) {
            Assert.fail("Error while clicking 'Add to Bag' at index " + index + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clickByIndexIfAnyAvailable() {
        int maxRetries = 3;
        int attempt = 0;
        boolean clicked = false;

        while (attempt < maxRetries && !clicked) {
            try {
                List<WebElement> sizeBoxes = driver.findElements(sizeBox);
                List<WebElement> sizeCharts = driver.findElements(sizeChat);

                if (!sizeBoxes.isEmpty()) {
                    WebElement box = sizeBoxes.get(0);
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", box);
                    box.click();
                    System.out.println("Clicked on size from sizeBox");
                    clicked = true;

                } else if (!sizeCharts.isEmpty()) {
                    WebElement chart = sizeCharts.get(0);
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", chart);
                    chart.click();
                    System.out.println("Clicked on size from sizeChart");
                    clicked = true;

                } else {
                    Assert.fail("No size elements found, skipping selection.");
                    clicked = true;
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element encountered. Retrying... Attempt " + (attempt + 1));
                clickAddToBagByIndex(2);
                attempt++;
            } catch (Exception e) {
                Assert.fail("Failed to select size: " + e.getMessage());
            }
        }
    }


}
