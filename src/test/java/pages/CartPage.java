package pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class CartPage extends BasePage{

    public final JavascriptExecutor js = (JavascriptExecutor) driver;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private final By cartIcon = By.xpath("//img[@alt=\"shopping-cart\"]");
    private final By viewBag = By.xpath("(//p[contains(text(), 'View Bag')])[2]");
    private final By viewBagOne = By.xpath("//p[contains(text(), 'View Bag')]");
    private final By pincodeBox = By.xpath("//input[@placeholder=\"Enter your PIN code\"]");
    private final By check = By.xpath("(//div[contains(text(), 'Check')])[2]");
    private final By sizeChart = By.xpath("//button[@role=\"combobox\"]");
    private final By increaseQuan = By.xpath("//img[@alt=\"plus\"]");
    private final By decreaseQuan = By.xpath("//img[@alt=\"minus\"]");
    private final By xMark = By.xpath("//img[@alt=\"remove product\"]");
    private final By moveToWishlist = By.xpath("//p[contains(text(), 'MOVE TO WISHLIST')]");
    private final By remove = By.xpath("//p[contains(text(), 'REMOVE')]");
    private final By giftWrap = By.xpath("//button[@role=\"checkbox\"]");
    public final By receiverName = By.id("Receiver’s Name");
    public final By message = By.id("Gift Message");
    public final By senderName = By.id("Sender’s Name");
    private final By saveGiftWrap = By.xpath("//p[contains(text(), 'SAVE GIFT DETAILS')]");


    public void clickOnCartIcon() {
        clickOnElement(cartIcon);
    }

    public void clickOnPincodeBox() {
        clickOnElement(pincodeBox);
    }

    public void enterThePincode(String str) {
        enterTextOnElement(pincodeBox, str);
    }

    public void clickOnCheckOption() {
        clickOnElement(check);
    }

    public void validateCartComponentsByText() {
        List<String> expectedComponents = Arrays.asList(
                "Offers for you",
                "Check Delivery",
                "GIFT WRAP",
                "HAVE A COUPON CODE?",
                "Price Details"
        );
        boolean allFound = true;
        for (String component : expectedComponents) {
            try {
                By locator = By.xpath("//div[contains(text(), '" + component + "')]");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                System.out.println("Found component: " + component);
            } catch (TimeoutException e) {
                System.out.println("Missing component: " + component);
                allFound = false;
            }
        }
        Assert.assertTrue(allFound, "Some expected components are missing on the cart page!");
    }

    public boolean isCartPage() {
        try {
            return wait.until(ExpectedConditions.urlContains("/cart/bag"));
        } catch (Exception e) {
            return false;
        }
    }

    public void clicksOnTheViewBag() {
        if(isWebElementDisplayed(viewBag)){
          clickOnElement(viewBag);
        } else {
          clickOnElement(viewBagOne);
        }
    }

    public void validateEmptyBag() {
        try {
            List<WebElement> emptyBagMessage = driver.findElements(
                    By.xpath("//div[contains(text(), 'Your Bag Feels Too Light!')]"));

            if (!emptyBagMessage.isEmpty()) {
                System.out.println("Your Bag Feels Too Light! message found in child window");
                Assert.fail("Assertion Failed: Empty bag message detected.");
            } else {
                System.out.println("Empty bag message not found.");
            }
        } catch (Exception e) {
            System.out.println("Exception while checking for empty bag message: " + e.getMessage());
        } finally {
            try {
            } catch (Exception e) {
                System.out.println("Error while closing child window: " + e.getMessage());
            }
        }
    }

    public void printAndValidatePriceDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            double mrp = 0, discount = 0, delivery = 0, total = 0;

            // Total MRP
            try {
                WebElement mrpElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//p[contains(text(), 'Total MRP')]/following-sibling::div")));
                String mrpText = mrpElem.getText().replaceAll("[^\\d.]", "");
                mrp = Double.parseDouble(mrpText);
                System.out.println("Total MRP        : ₹" + mrpText);
            } catch (Exception e) {
                System.out.println("Total MRP not found");
            }
            // Offer Discount
            try {
                WebElement discountElem = driver.findElement(
                        By.xpath("//p[contains(text(), 'Offer Discount')]/following-sibling::div"));
                String discountText = discountElem.getText().replaceAll("[^\\d.]", "");
                discount = Double.parseDouble(discountText);
                System.out.println("Offer Discount   : ₹" + discountText);
            } catch (Exception e) {
                System.out.println("Offer Discount not found");
            }
            // Your Total Savings
            try {
                WebElement savingsElem = driver.findElement(
                        By.xpath("//div[contains(text(), 'Your Total Savings')]/following-sibling::div"));
                String savingsText = savingsElem.getText().replaceAll("[^\\d.]", "");
                System.out.println("Your Total Saving: ₹" + savingsText);
            } catch (Exception e) {
                System.out.println("Your Total Saving not found");
            }
            // Delivery Fee
            try {
                WebElement deliveryElem = driver.findElement(
                        By.xpath("//p[contains(text(), 'Delivery Fee')]/following-sibling::div"));
                String deliveryText = deliveryElem.getText().trim();
                if (deliveryText.equalsIgnoreCase("Free")) {
                    delivery = 0;
                    System.out.println("Delivery Fee     : Free");
                } else {
                    delivery = Double.parseDouble(deliveryText.replaceAll("[^\\d.]", ""));
                    System.out.println("Delivery Fee     : ₹" + delivery);
                }
            } catch (Exception e) {
                System.out.println("Delivery Fee not found");
            }
            // Total Payable
            try {
                WebElement totalElem = driver.findElement(
                        By.xpath("//div[contains(@class,'rounded-b-sm')]//div[contains(text(),'₹')]"));
                String totalText = totalElem.getText().replaceAll("[^\\d.]", "");
                total = Double.parseDouble(totalText);
                System.out.println("Total Payable    : ₹" + totalText);
            } catch (Exception e) {
                System.out.println("Total Payable not found – cannot validate");
                Assert.fail("Total Payable not found");
            }
            // Final validation (if we have Total MRP and Total Payable only)
            if (mrp > 0 && total > 0) {
                double expected = mrp - discount + delivery;
                Assert.assertEquals(total, expected, 0.1, "Total payable mismatch");
            } else {
                System.out.println("Skipped assertion due to missing MRP or Total");
            }
        } catch (Exception e) {
            Assert.fail("Error extracting price breakdown: " + e.getMessage());
        }
    }

    public void emptyBagValidation() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
            List<WebElement> mrpSection = driver.findElements(
                    By.xpath("//p[contains(text(), 'Total MRP')]/following-sibling::div")
            );
            if (!mrpSection.isEmpty()) {
                printAndValidatePriceDetails();
            } else {
                System.out.println("Price details not found, checking for empty bag...");
                validateEmptyBag();
            }
        } catch (Exception e) {
            Assert.fail("Exception during price details check or fallback: " + e.getMessage());
        }
    }

    public void clicksOnTheSizeChange(int index) {

        try {
            waitForOverlayToDisappear();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> comboButtons = wait.until(ExpectedConditions.
                    presenceOfAllElementsLocatedBy(sizeChart));

            if (index >= 0 && index < comboButtons.size()) {
                WebElement comboBox = comboButtons.get(index);
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", comboBox);
                wait.until(ExpectedConditions.elementToBeClickable(comboBox)).click();
                System.out.println("Clicked combobox at index: " + index);
            }
        } catch (Exception e) {
            Assert.fail("Failed to click combobox at index " + index + ": " + e.getMessage());
        }
    }

    public void clicksOnTheSizeChart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='option']")));
            // Click the first visible option
            boolean selected = false;
            for (WebElement option : options) {
                if (option.isDisplayed()) {
//                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                    String selectedText = option.getText().trim();
                    option.click();
                    System.out.println("Selected Size: " + selectedText);
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                Assert.fail("No visible size option found to select.");
            }
        } catch (Exception e) {
            Assert.fail("Error selecting combobox size: " + e.getMessage());
        }
    }

    public void adjustQuantity(boolean increaseChoice, int times) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement increase = wait.until(ExpectedConditions.presenceOfElementLocated(increaseQuan));
            WebElement decrease = wait.until(ExpectedConditions.presenceOfElementLocated(decreaseQuan));

            for (int i = 0; i < times; i++) {

                WebElement target = increaseChoice ? increase : decrease;
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", target);

                try {
                    By overlayLocator = By.cssSelector("div[class*='fixed'], div[class*='overlay']");
                    new WebDriverWait(driver, Duration.ofSeconds(3))
                            .until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
                } catch (Exception ignored) {
                }
                js.executeScript("arguments[0].click();", target);
                System.out.println(increaseChoice ? "Quantity Increased" : "Quantity Decreased");
                Thread.sleep(800);
            }
            System.out.println("Total actions performed: " + times);
        } catch (Exception e) {
            Assert.fail("Failed to change quantity: " + e.getMessage());
        }
    }

    public void clickOnTheMoveToWishlist() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> moveWishlist = driver.findElements
                    (By.xpath("//p[contains(text(), \"Already Wishlisted\")]"));
            if(!moveWishlist.isEmpty()) {
                System.out.println("Product is already in wishlist. Removing the product");
                clickOnElement(remove);
            }
            wait.until(ExpectedConditions.elementToBeClickable(moveToWishlist)).click();
        } catch (Exception e) {
            Assert.fail("Failed to move product to wishlist: " + e.getMessage());
        }
    }

    public void clickOnGiftWrap() {
        waitForOverlayToDisappear();
        clickOnElement(giftWrap);
    }

    public void clickOnSaveGiftWrap() {
        clickOnElement(saveGiftWrap);
    }

    public void clickOnXmark() {
        reloadPage();
        clickOnElement(xMark, 0);
    }

    public void clickOnRemove() {
        clickOnElement(remove);
    }

}
