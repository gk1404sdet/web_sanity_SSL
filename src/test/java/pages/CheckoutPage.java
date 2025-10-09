package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class CheckoutPage extends BasePage {

    private final JavascriptExecutor js = (JavascriptExecutor) driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }


    private final By placeOrder = By.xpath("//p[contains(text(), 'PLACE ORDER')]");
    private final By continueButton = By.xpath("//p[contains(text(), 'CONTINUE')]");
    private final By continueShopping = By.xpath("//p[contains(text(), 'CONTINUE SHOPPING')]");
    public final By productList = By.xpath("//div[@data-product-id]");
    private final By reviewShop = By.xpath("//div[contains(text(), 'RATE YOUR SHOPPING EXPERIENCE')]");
    private final By submitButton = By.xpath("//p[contains(text(), 'submit')]");
    private final By error = By.xpath("//div[contains(text(), 'What do you hate more than a broken item? A 404 error like this one!')]");
    private final By priceDetails = By.xpath("//div[@class='flex flex-col items-start sm:px-4 md:px-0 md:pt-0 lg:px-0 xs:pt-4 mm:px-0']");
    private final By outOfStockCheck = By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'OUT OF STOCK')]");
    private final By sizeBtn = By.xpath("//div[contains(@class,'overflow-x-auto')]//button");
    private final By addToBag = By.xpath("//img[@alt=\"bag_white\"]");
    private final By popUpViewBag = By.xpath("(//p[contains(text(), 'View Bag')])[2]");
    private final By viewBag = By.xpath("//p[contains(text(), 'View Bag')]");
    private final By addressBox = By.xpath("//div[@class=\"rounded-full border border-black p-1\"]");
    private final By item = By.cssSelector("div[data-state='open'][class*='fixed']");
    private final By cod = By.xpath("//div[contains(text(), 'COD')]");
    private final By containerLocator = By.xpath("//div[@class='md: mt-4 flex flex-col gap-5 md:max-w-[1200px] md:flex-row md:flex-wrap md:justify-between md:gap-6 lg:mt-6 lg:gap-8 xl:gap-[72px]']");
    private final By deliverInfo = By.xpath("(//div[@class='rounded border border-lightGray p-4'])[2]");
    private final By paymentMode = By.xpath("//*[contains(text(),'Credit') or contains(text(),'Netbanking') or contains(text(),'Wallets')]");
    private final By iFrame = By.name("HyperServices");
    private final By cardBlockLocator = By.xpath("//div[@class='rounded border border-lightGray md:basis-[31%] lg:rounded-lg']");
    private final By cardBlockEle = By.xpath(".//div | .//p");
    private final By wallet = By.xpath(   "(//*[text()='Wallets' or normalize-space()='Wallets'])[8]");
    private final By phonePe = By.xpath("//*[text()='PhonePe' or contains(text(),'Phone Pe')]");
    private final By proceedBtn = By.xpath("//*[text()='Proceed' or contains(text(),'Proceed')]");
    private final By upi = By.xpath("(//article[contains(text(),'UPI')])[3]");
    private final By editUpi = By.xpath("//input[@id=\"2000011\"]");
    private final By proceedToPay = By.xpath("(//article[contains(text(), 'Proceed to pay ')])[2]");
    private final By filterLocator = By.xpath("//div[@class='cursor-pointer text-nowrap rounded-[36px] border border-neutral-300 px-2.5 py-3 md:rounded bg-transparent']");
    private final By ratingContainer = By.cssSelector("svg[data-testid='rating-component-star']:nth-of-type(4)");

    public boolean clickProductByIndex(By locator,int index) {
        try {
            waitForOverlayToDisappear();
            List<WebElement> productList = driver.findElements(locator);

            if (index >= 0 && index < productList.size()) {
                WebElement product = productList.get(index);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.elementToBeClickable(product)).click();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, String> fetchPDPDetails() {
        Map<String, String> productDetails = new HashMap<>();
        try {
            waitForOverlayToDisappear();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            String originalWindow = driver.getWindowHandle();

            wait.until(driver -> driver.getWindowHandles().size() > 1);
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
            List<WebElement> errorElements = driver.findElements(error);
            if (!errorElements.isEmpty()) {
                productDetails.put("error", "404 Error: Product not found or page is broken.");
                return productDetails;
            }

            // full product block container
            WebElement productContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(priceDetails));
            String fullText = productContainer.getText().trim();
            String[] lines = fullText.split("\n");

            productDetails.put("brand", lines.length >= 1 ? lines[0].replace("Brand:", "").trim() : "N/A");
            productDetails.put("title", lines.length >= 2 ? lines[1].trim() : "N/A");
            productDetails.put("price", lines.length >= 3 ? lines[2].trim() : "N/A");
            productDetails.put("mrp", lines.length >= 5 ? lines[4].trim() : "N/A");
            productDetails.put("discount", lines.length >= 6 ? lines[5].trim() : "N/A");
        } catch (Exception e) {
            productDetails.put("error", "Exception while fetching PDP details: " + e.getMessage());
        }
        return productDetails;
    }

    public boolean isOutOfStockDisplayed() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> outOfStock = driver.findElements(outOfStockCheck);
            return !outOfStock.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectSize() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> sizeButtons = driver.findElements(sizeBtn);

            if (sizeButtons.isEmpty()) {
                return false;
            }
            for (WebElement btn : sizeButtons) {
                String classAttr = btn.getAttribute("class");
                if (classAttr != null && classAttr.contains("disabled:pointer-events-none") && !classAttr.contains("opacity-[40%]")) {
                    btn.click();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

        public boolean clickAddToBagOrViewBagFallback() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> addToBagButtons = driver.findElements(addToBag);
            Optional<WebElement> visibleBtn = addToBagButtons.stream()
                    .filter(btn -> btn.isDisplayed() && btn.isEnabled())
                    .findFirst();
            if (visibleBtn.isPresent()) {
                WebElement addToBagBtn = visibleBtn.get();
                wait.until(ExpectedConditions.elementToBeClickable(addToBagBtn)).click();
                return true;
            } else {
                clickViewBagWithPriority();
                return false;
            }
        } catch (Exception e) {
            clickViewBagWithPriority();
            return false;
        }
    }

    public void clickViewBagWithPriority() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Try the 2nd View Bag
            List<WebElement> secondViewBagList = driver.findElements(popUpViewBag);
            if (!secondViewBagList.isEmpty() && secondViewBagList.get(0).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(secondViewBagList.get(0))).click();
                return;
            }
        } catch (Exception e) {}

        try {
            // Fallback: Try the first View Bag
            List<WebElement> firstViewBagList = driver.findElements(viewBag);
            if (!firstViewBagList.isEmpty() && firstViewBagList.get(0).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(firstViewBagList.get(0))).click();
            } else {}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickOnThePlaceOrder() {

        waitForOverlayToDisappear();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement placeOrderBtn = wait.until(ExpectedConditions.elementToBeClickable(placeOrder));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrderBtn);
            Thread.sleep(500);
            placeOrderBtn.click();
        } catch (ElementClickInterceptedException e) {

            WebElement placeOrderBtn = driver.findElement(placeOrder);
            js.executeScript("arguments[0].click();", placeOrderBtn);
        } catch (Exception ex) {}
    }

    public void selectTheAddress() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(item));
        wait.until(ExpectedConditions.elementToBeClickable(addressBox)).click();
    }

    public void clickOnContinueButton() {
        clickOnElement(continueButton);
    }

    public void selectingTheCOD() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> elements = driver.findElements(cod);
            if (elements.isEmpty()) {}
            WebElement element = elements.get(0);
            js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'nearest'});", element);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {}
    }

    public Map<String, String> printOrderSummaryDetails() {
        waitForOverlayToDisappear();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Map<String, String> orderDetails = new HashMap<>();
        try {

        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(containerLocator));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", container);

        List<WebElement> divs = container.findElements(By.xpath(".//div"));

        String orderId = "";
        String orderPlaced = "";
        String orderStatus = "";
        boolean statusCaptured = false;

        for (int i = 0; i < divs.size(); i++) {
            String text = divs.get(i).getText().trim();

            if (text.equalsIgnoreCase("Order ID#") && (i + 1 < divs.size())) {
                orderId = divs.get(i + 1).getText().trim();
            } else if (text.equalsIgnoreCase("Order Placed") && (i + 1 < divs.size())) {
                orderPlaced = divs.get(i + 1).getText().trim();
            } else if (!statusCaptured && text.toLowerCase().contains("status")) {
                orderStatus = text.replace("Order Status : ", "").trim();
                statusCaptured = true;
            }
        }

        orderDetails.put("OrderId", orderId);
        orderDetails.put("OrderPlaced", orderPlaced);
        orderDetails.put("OrderStatus", orderStatus);
    } catch (Exception e) {
            String currentWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();
            driver.close();

            for (String handle : allWindows) {
                if (!handle.equals(currentWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
           throw new RuntimeException("Test Failed: Order has Not Placed Successfully", e);
        }
        return orderDetails;
    }

    public Map<String, String> printDeliveryAddressDetails() {

        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(deliverInfo));
        js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", container);

        List<WebElement> lines = container.findElements(By.xpath(".//div"));

        String deliveryMode = "";
        StringBuilder addressBuilder = new StringBuilder();
        String phone = "";

        for (WebElement line : lines) {
            String text = line.getText().trim();

            if (text.equalsIgnoreCase("Standard Delivery") || text.toLowerCase().contains("delivery")) {
                deliveryMode = text;
            } else if (text.toLowerCase().contains("phone")) {
                phone = text;
            } else if (!text.isEmpty()) {
                addressBuilder.append(text).append("\n");
            }
        }

        String address = addressBuilder.toString().replace(deliveryMode, "").replace(phone, "").trim();

        Map<String, String> deliveryDetails = new HashMap<>();
        deliveryDetails.put("DeliveryMode", deliveryMode);
        deliveryDetails.put("Address", address);
        deliveryDetails.put("Phone", phone);
        return deliveryDetails;
    }

    public List<String> verifiesPaymentModeIframe() {
        List<String> detectedModes = new ArrayList<>();
        try {
            waitForOverlayToDisappear();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Switch to iframe safely
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
            wait.until(ExpectedConditions.presenceOfElementLocated(paymentMode));

            // Define all expected payment mode texts (you can customize this)
            List<String> expectedModes = Arrays.asList(
                    "Credit / Debit Card", "Netbanking", "UPI", "Wallets",
                    "EMI on Cards", "Saved Cards"
            );

            for (String mode : expectedModes) {
                List<WebElement> elements = driver.findElements(By.xpath("//*[normalize-space(text())='" + mode + "']"));
                if (!elements.isEmpty()) {
                    detectedModes.add(mode);
                }
            }
        } catch (Exception e) {}
          finally {
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignored) {}
        }
        return detectedModes;
    }

    public void printCardBlockDetails() {

        WebElement block = wait.until(ExpectedConditions.visibilityOfElementLocated(cardBlockLocator));
        js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", block);

        List<WebElement> elements = block.findElements(cardBlockEle);

        LinkedHashMap<String, String> priceDetails = new LinkedHashMap<>();
        String lastLabel = "";

        for (WebElement el : elements) {
            String text = el.getText().trim();

            if (text.equalsIgnoreCase("PRICE DETAILS") || text.isEmpty()) {
                continue;
            }

            if (text.matches("(?i)(Total MRP|Offer Discount|COD Payment|Total Paid Amount|Mode of Payment|1 Item|\\d+ Items?)")) {
                if (text.matches("\\d+ Items?")) {
                    priceDetails.put("Items", text);
                } else if (lastLabel.isEmpty()) {
                    lastLabel = text;
                } else {
                    priceDetails.put(lastLabel, text);
                    lastLabel = "";
                }
            } else if (!lastLabel.isEmpty()) {
                priceDetails.put(lastLabel, text);
                lastLabel = "";
            }
        }
        for (Map.Entry<String, String> entry : priceDetails.entrySet()) {
        }
    }

    public void clickOnTheContinueShoppingOrClose() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String childWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        try {
            List<WebElement> continueButtons = driver.findElements(continueShopping);

            if (!continueButtons.isEmpty() && continueButtons.get(0).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(continueButtons.get(0))).click();
                Thread.sleep(1000);
            } else {}
        } catch (Exception e) {}
          finally {
            // close child window and return to parent
            driver.close();
            for (String handle : allWindows) {
                if (!handle.equals(childWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
        }
    }

    public void selectWalletAndProceed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Switch to HyperServices iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));
            Thread.sleep(10);
            // Click on "Wallets"
            WebElement walletsTab = wait.until(ExpectedConditions.elementToBeClickable(wallet));
//            WebElement upiTab = wait.until(ExpectedConditions.elementToBeClickable(upi));
            js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'nearest'});", walletsTab);

            walletsTab.click();
//            upiTab.click();
            Thread.sleep(1000);

            // Select "PhonePe"
            wait.until(ExpectedConditions.elementToBeClickable(phonePe)).click();

            // Edit "UPI"
//            wait.until(ExpectedConditions.elementToBeClickable(editUpi)).click();

            // Enter "UPI"
//            wait.until(ExpectedConditions.elementToBeClickable(editUpi)).sendKeys("test@ybl");

            // Click on "proceed to Pay"
//            wait.until(ExpectedConditions.elementToBeClickable(proceedToPay)).click();

            // Click on "Proceed"
            wait.until(ExpectedConditions.elementToBeClickable(proceedBtn)).click();

            // Switch back to main document
            driver.switchTo().defaultContent();
        } catch (Exception e) {}
    }

    public void rateShopping() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      wait.until(ExpectedConditions.elementToBeClickable(ratingContainer)).click();
    }

    public void clickElementByIndex(int indexToClick) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(filterLocator));

        if (indexToClick >= 0 && indexToClick < elements.size()) {
            WebElement elementToClick = elements.get(indexToClick);
            wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
        } else {
            throw new IllegalArgumentException("Invalid index: " + indexToClick);
        }
    }

    public void scrollToReview() {
        scrollToElement(reviewShop);
    }

    public void clickOnSubmitButton() {
        clickOnElement(submitButton);
    }

}