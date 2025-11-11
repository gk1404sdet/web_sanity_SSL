package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WishlistPage extends BasePage {

    public final JavascriptExecutor js = (JavascriptExecutor) driver;

    // ---------- Locators ----------
    private final By wishlistIcon = By.xpath("//img[@alt=\"wish-list\"]");
    public final By wishlistProducts = By.xpath("//div[@class=\"bg-transparent pr-0\"]");
    private final By wishlistIconPLP = By.xpath("//img[@alt=\"empty_icon\"]");
    private final By removeWishlistIconPLP = By.xpath("//img[@alt=\"filled_icon\"]");
    private final By wishlistIconPDP = By.xpath("//p[contains(text(), 'WISHLIST')]");
    private final By addToBag = By.xpath("//div[contains(text(), 'Add to Bag')]");
    private final By sizeBox = By.xpath("//div[@class='cursor-pointer bg-[#F2F2F2] px-[1.111vw] py-[0.833vw] xxl:px-[16px] xxl:py-[12px] false']");
    private final By sizeChat = By.xpath("//img[@alt='product_card_size_chart']");
    public final By youMight = By.xpath("//span[contains(text(), 'You Might Also ')]");
    private final By brandLocator = By.xpath(".//div[@class='px-1 pb-1 pt-4 md:py-3']");
    private final By priceLocator = By.xpath(".//div[contains(text(),'₹')]");
    private final By overlay = By.cssSelector("div.fixed.inset-0.z-\\[102\\]");
    private final By overlayWishlist = By.cssSelector("div.fixed.inset-0.bg-neutral-900\\/90");


    public WishlistPage(WebDriver driver) {
        super(driver);
    }


    // ---------- Common Actions ----------
    public void clickOnWishlistIcon() {
        waitForOverlayToDisappear();
        clickOnElement(wishlistIcon);
    }

    public void clickOnWishlistPDP() {
        clickOnElement(wishlistIconPDP);
    }

    public void fetchProductList(By blockLocator) {
        try {
            waitForOverlayToDisappear();
            waitForPresenceOfElement(blockLocator);

            List<WebElement> blocks = driver.findElements(blockLocator);
            int count = 0;

            for (WebElement block : blocks) {
                String brand = "";
                String price = "";

                try {
                    WebElement brandEl = block.findElement(brandLocator);
                    brand = brandEl.getText().trim();
                } catch (Exception ignored) {
                }

                try {
                    WebElement priceEl = block.findElement(priceLocator);
                    price = priceEl.getText().trim();
                } catch (Exception ignored) {
                }

                if (!brand.isEmpty() && !price.isEmpty()) {
                    count++;
                }
            }
        } catch (Exception e) {}
    }

    public void addProductsToWishlist(int count) {
        waitForOverlayToDisappear();
        waitForPresenceOfElement(wishlistIconPLP);

        List<WebElement> wishlistIcons = driver.findElements(wishlistIconPLP);
        int added = 0;

        for (WebElement icon : wishlistIcons) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", icon);
                try {
                    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    shortWait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
                } catch (Exception ignored) {}

                // Click to add to wishlist
                wait.until(ExpectedConditions.elementToBeClickable(icon)).click();
                added++;

                if (added == count) {
                    break;
                }
            } catch (Exception ignored) {}
        }
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
                        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                        shortWait.until(ExpectedConditions.invisibilityOfElementLocated(overlayWishlist));
                    } catch (Exception ignored) {}
                    wait.until(ExpectedConditions.elementToBeClickable(icon)).click();
                    removed++;

                    if (removed == count) break;
                }
            } catch (Exception ignored) {}
        }
    }

    public void clickAddToBagByIndex(int index) {
        waitForOverlayToDisappear();
        waitForPresenceOfElement(addToBag);

        List<WebElement> addToBagButtons = driver.findElements(addToBag);

        if (!addToBagButtons.isEmpty() && index >= 0 && index < addToBagButtons.size()) {
            WebElement button = addToBagButtons.get(index);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
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
                    wait.until(ExpectedConditions.elementToBeClickable(box)).click();
                    clicked = true;

                } else if (!sizeCharts.isEmpty()) {
                    WebElement chart = sizeCharts.get(0);
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", chart);
                    wait.until(ExpectedConditions.elementToBeClickable(chart)).click();
                    clicked = true;

                } else {
                    clicked = true;
                }
            } catch (StaleElementReferenceException e) {
                attempt++;
                clickAddToBagByIndex(2);
            } catch (Exception e) {
                break;
            }
        }
    }

}
