package pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class CartPage extends BasePage{

    public final JavascriptExecutor js = (JavascriptExecutor) driver;


    // ---------- Locators ----------
    private final By cartIcon = By.xpath("//img[@alt=\"shopping-cart\"]");
    private final By viewBag = By.xpath("(//p[contains(text(), 'View Bag')])[2]");
    private final By viewBagOne = By.xpath("//p[contains(text(), 'View Bag')]");
    public final By pincodeBox = By.xpath("//input[@placeholder=\"Enter your PIN code\"]");
    private final By check = By.xpath("(//div[contains(text(), 'Check')])[2]");
    private final By sizeChart = By.xpath("//button[@role=\"combobox\"]");
    private final By increaseQuan = By.xpath("//img[@alt=\"plus\"]");
    private final By decreaseQuan = By.xpath("//img[@alt=\"minus\"]");
    private final By xMark = By.xpath("//img[@alt=\"remove product\"]");
    private final By moveToWishlist = By.xpath("//p[contains(text(), 'MOVE TO WISHLIST')]");
    private final By remove = By.xpath("//p[contains(text(), 'REMOVE')]");
    private final By giftWrap = By.xpath("//button[@role=\"checkbox\"]");
    private final By receiverName = By.id("Receiver’s Name");
    private final By message = By.id("Gift Message");
    private final By senderName = By.id("Sender’s Name");
    private final By saveGiftWrap = By.xpath("//p[contains(text(), 'SAVE GIFT DETAILS')]");
    private final By offersForYou  = By.xpath("//div[contains(text(),'Offers for you')]");
    private final By checkDelivery = By.xpath("//div[contains(text(),'Check Delivery')]");
    private final By giftWrapCom = By.xpath("//div[contains(text(),'GIFT WRAP')]");
    private final By couponCode = By.xpath("//div[contains(text(),'HAVE A COUPON CODE?')]");
    private final By priceDetails  = By.xpath("//div[contains(text(),'Price Details')]");
    private final By emptyBag = By.xpath("//div[contains(text(), 'Your Bag Feels Too Light!')]");
    private final By mrpProduct =  By.xpath("//p[contains(text(), 'Total MRP')]/following-sibling::div");
    private final By discountProduct = By.xpath("//p[contains(text(), 'Offer Discount')]/following-sibling::div");
    private final By savingsProduct = By.xpath("//div[contains(text(), 'Your Total Savings')]/following-sibling::div");
    private final By deliveryFeeProduct = By.xpath("//p[contains(text(), 'Delivery Fee')]/following-sibling::div");
    private final By totalFeeProduct =  By.xpath("//div[contains(@class,'rounded-b-sm')]//div[contains(text(),'₹')]");
    private final By sizeOption = By.xpath("//div[@role='option']");
    private final By overlayLocator = By.cssSelector("div[class*='fixed'], div[class*='overlay']");
    private final By addAddress = By.xpath("//p[contains(text(), 'ADD NEW ADDRESS')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Common Actions ----------
    public void clickOnCartIcon() {
        clickOnElement(cartIcon);
    }

    public void clickOnAddAddress() {
        waitForOverlayToDisappear();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement freshButton = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(addAddress)
        ));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", freshButton);
        freshButton.click();
    }

    public void clickOnPincodeBox() {
        waitForOverlayToDisappear();
        clickOnElement(pincodeBox);
    }

    public void enterThePincode(String str) {
        waitForOverlayToDisappear();
        enterTextOnElement(pincodeBox, str);
    }

    public void clickOnCheckOption() {
        waitForOverlayToDisappear();
        clickOnElement(check);
    }

    public boolean validateCartComponentsByText(List<String> expectedSections) {
        List<By> expectedComponents = Arrays.asList(
                offersForYou,
                checkDelivery,
                giftWrapCom,
                couponCode,
                priceDetails
        );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean allFound = true;
        for (By locator : expectedComponents) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            } catch (TimeoutException e) {
                allFound = false;
            }
        }
        return allFound;
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

    public boolean validateEmptyBag() {
        try {
            List<WebElement> emptyBagMessage = driver.findElements(emptyBag);
            return emptyBagMessage.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean printAndValidatePriceDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            double mrp = 0, discount = 0, delivery = 0, total = 0;

            // Total MRP
            try {
                WebElement mrpElem = wait.until(ExpectedConditions.visibilityOfElementLocated(mrpProduct));
                String mrpText = mrpElem.getText().replaceAll("[^\\d.]", "");
                mrp = Double.parseDouble(mrpText);
            } catch (Exception e) {}

            // Offer Discount
            try {
                WebElement discountElem = driver.findElement(discountProduct);
                String discountText = discountElem.getText().replaceAll("[^\\d.]", "");
                discount = Double.parseDouble(discountText);
            } catch (Exception e) {}

            // Your Total Savings
            try {
                WebElement savingsElem = driver.findElement(savingsProduct);
                String savingsText = savingsElem.getText().replaceAll("[^\\d.]", "");
            } catch (Exception e) {}

            // Delivery Fee
            try {
                WebElement deliveryElem = driver.findElement(deliveryFeeProduct);
                String deliveryText = deliveryElem.getText().trim();
                if (deliveryText.equalsIgnoreCase("Free")) {
                    delivery = 0;
                } else {
                    delivery = Double.parseDouble(deliveryText.replaceAll("[^\\d.]", ""));
                }
            } catch (Exception e) {}

            // Total Payable
            try {
                WebElement totalElem = driver.findElement(totalFeeProduct);
                String totalText = totalElem.getText().replaceAll("[^\\d.]", "");
                total = Double.parseDouble(totalText);
            } catch (Exception e) {
                return false;
            }

            // Final validation (if we have Total MRP and Total Payable only)
            if (mrp > 0 && total > 0) {
                double expected = mrp - discount + delivery;
                return Math.abs(total - expected) < 0.1;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean emptyBagValidation() {
        try {
            List<WebElement> mrpSection = driver.findElements(mrpProduct);
            if (!mrpSection.isEmpty()) {
               return printAndValidatePriceDetails();
            } else {
               return validateEmptyBag();
            }
        } catch (Exception e) {
            return false;
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
            }
        } catch (Exception e) {}
    }

    public void clicksOnTheSizeChart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(sizeOption));
            // Click the first visible option

            for (WebElement option : options) {
                if (option.isDisplayed()) {
                    option.click();
                    break;
                }
            }
        } catch (Exception e) {}
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
                        new WebDriverWait(driver, Duration.ofSeconds(3))
                                .until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
                    } catch (Exception ignored) {
                    }
                    js.executeScript("arguments[0].click();", target);
                    Thread.sleep(800);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to change quantity", e);
            }
        }

    public void clickOnTheMoveToWishlist() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> moveWishlist = driver.findElements(moveToWishlist);
            if(moveWishlist.isEmpty()) {
                clickOnElement(remove);
            }
            wait.until(ExpectedConditions.elementToBeClickable(moveToWishlist)).click();
        } catch (Exception e) {}
    }

    public void clickOnGiftWrap() {
        waitForOverlayToDisappear();
        clickOnElement(giftWrap);
    }

    public void enterTheReceiverName(String revName) {
        enterTextOnElement(receiverName, revName);
    }

    public void enterTheMessage(String msg) {
        enterTextOnElement(message, msg);
    }

    public void enterTheSenderName(String sendName) {
        enterTextOnElement(senderName, sendName);
    }

    public void clickOnSaveGiftWrap() {
        clickOnElement(saveGiftWrap);
    }

    public void clickOnXmark() {
        waitForOverlayToDisappear();
        clickOnElement(xMark, 0);
    }

    public void clickOnRemove() {
        clickOnElement(remove);
    }

}
