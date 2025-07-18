package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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


    public void clickProductByIndex(By locator,int index) {
        try {
            waitForOverlayToDisappear();
            List<WebElement> productList = driver.findElements(locator);

            if (index >= 0 && index < productList.size()) {
                WebElement product = productList.get(index);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
                Thread.sleep(500);
                product.click();
                System.out.println("Clicked product at index: " + index);
            } else {
                Assert.fail("Invalid index: " + index + " | Total products: " + productList.size());
            }
        } catch (Exception e) {
            Assert.fail("Error clicking product at index " + index + ": " + e.getMessage());
        }
    }

    public void fetchPDPDetails() {
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
            // Check for 404 error banner
            List<WebElement> errorElements = driver.findElements(By.xpath("//div[contains(text(), 'What do you hate more than a broken item? A 404 error like this one!')]"));
            if (!errorElements.isEmpty()) {
                Assert.fail("404 Error: Product not found or page is broken.");
                return;
            }

            // full product block container
            WebElement productContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='flex flex-col items-start sm:px-4 md:px-0 md:pt-0 lg:px-0 xs:pt-4 mm:px-0']")
            ));
            String fullText = productContainer.getText().trim();
            String[] lines = fullText.split("\n");
            String brand = "N/A", title = "N/A", price = "N/A", mrp = "N/A", discount = "N/A";

            if (lines.length >= 6) {
                brand = lines[0].replace("Brand:", "").trim();
                title = lines[1].trim();
                price = lines[2].trim();
                mrp = lines[4].trim();
                discount = lines[5].trim();
            }
            System.out.println("Brand Title : " + brand);
            System.out.println("Product Title : " + title);
            System.out.println("Price : " + price);
            System.out.println("MRP : " + mrp);
            System.out.println("Discount : " + discount);
        } catch (Exception e) {
            Assert.fail("Failed to fetch PDP details with window handling: " + e.getMessage());
        }
    }

    public boolean isOutOfStockDisplayed() {
        try {
            List<WebElement> outOfStock = driver.findElements(
                    By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'OUT OF STOCK')]")
            );
            return !outOfStock.isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking out-of-stock: " + e.getMessage());
            return false;
        }
    }

    public List<WebElement> printAvailableSizes() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> sizeButtons = driver.findElements(
                    By.xpath("//div[contains(@class,'overflow-x-auto')]//button")
            );
            if (sizeButtons.isEmpty()) {
                System.out.println("No size options present on this product.");
                return new ArrayList<>();
            }

            System.out.println("----- Available Sizes -----");
            for (int i = 0; i < sizeButtons.size(); i++) {
                String sizeText = sizeButtons.get(i).getText().trim();
                if (!sizeText.isEmpty()) {
                    System.out.println(i + " - " + sizeText);
                }
            }
            return sizeButtons;
        } catch (Exception e) {
            System.out.println("Exception while fetching sizes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean selectSize() {
        try {
            waitForOverlayToDisappear();
            List<WebElement> sizeButtons = printAvailableSizes();

            if (sizeButtons.isEmpty()) {
                System.out.println("No size options present. Proceeding without selection.");
                return false;
            }
            for (WebElement btn : sizeButtons) {
                String classAttr = btn.getAttribute("class");
                if (classAttr != null && classAttr.contains("disabled:pointer-events-none") && !classAttr.contains("opacity-[40%]")) {
                    btn.click();
                    System.out.println("Selected size: " + btn.getText().trim());
                    return true;
                }
            }
            System.out.println("Size options are all disabled or not clickable.");
            return false;
        } catch (Exception e) {
            System.out.println("Exception during size selection: " + e.getMessage());
            return false;
        }
    }

    public boolean clickAddToBagOrViewBagFallback() {
        try {
            List<WebElement> addToBagButtons = driver.findElements(By.xpath("//p[contains(text(), 'Add to bag')]"));
            if (!addToBagButtons.isEmpty() && addToBagButtons.get(0).isDisplayed()) {
                WebElement addToBagBtn = addToBagButtons.get(0);
                wait.until(ExpectedConditions.elementToBeClickable(addToBagBtn)).click();
                System.out.println("Clicked on: Add to Bag");
                return true;
            } else {
                System.out.println("Add to Bag not visible. Clicking View Bag instead...");
                clickViewBagWithPriority();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error occurred in Add to Bag click. Falling back to View Bag...");
            clickViewBagWithPriority();
            return false;
        }
    }

    public void clickViewBagWithPriority() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Try the 2nd View Bag
            List<WebElement> secondViewBagList = driver.findElements(By.xpath("(//p[contains(text(), 'View Bag')])[2]"));
            if (!secondViewBagList.isEmpty() && secondViewBagList.get(0).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(secondViewBagList.get(0))).click();
                System.out.println("Clicked on: View Bag from PDP (2nd)");
                return;
            }
        } catch (Exception e) {
            System.out.println("Second View Bag not clickable or visible.");
        }

        try {
            // Fallback: Try the first View Bag
            List<WebElement> firstViewBagList = driver.findElements(By.xpath("//p[contains(text(), 'View Bag')]"));
            if (!firstViewBagList.isEmpty() && firstViewBagList.get(0).isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(firstViewBagList.get(0))).click();
                System.out.println("Clicked on: View Bag from Pop-Up (1st)");
            } else {
                System.out.println("No visible View Bag options found.");
            }
        } catch (Exception e) {
            System.out.println("Failed to click any View Bag.");
            e.printStackTrace();
        }
    }

    public void clickOnThePlaceOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement placeOrderBtn = wait.until(ExpectedConditions.elementToBeClickable(placeOrder));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrderBtn);
            Thread.sleep(500);
            placeOrderBtn.click();
            System.out.println("Clicked on Place Order");
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element click intercepted. Retrying with JS");

            WebElement placeOrderBtn = driver.findElement(placeOrder);
            js.executeScript("arguments[0].click();", placeOrderBtn);
            System.out.println("Clicked using JavaScript as fallback");
        } catch (Exception ex) {
            System.out.println("Failed to click 'Place Order': " + ex.getMessage());
            Assert.fail("Click failed");
        }
    }

    public void selectTheAddress() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div[data-state='open'][class*='fixed']")));

        wait.until(ExpectedConditions.
                elementToBeClickable
                        (By.xpath("//div[@class=\"rounded-full border border-black p-1\"]"))).click();
    }

    public void clickOnContinueButton() {
        clickOnElement(continueButton);
    }

    public void selectingTheCOD() {
        try {
            waitForOverlayToDisappear();
            By cod = By.xpath("//div[contains(text(), 'COD')]");
            List<WebElement> elements = driver.findElements(cod);
            if (elements.isEmpty()) {
                Assert.fail("COD is not available because the cart amount exceeds the eligible limit. Please reduce the order value to proceed with Cash on Delivery");
            }
            WebElement element = elements.get(0);
            js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'nearest'});", element);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            System.out.println("COD option selected.");
        } catch (Exception e) {
            Assert.fail("Failed to select COD option: " + e.getMessage());
        }
    }

    public void printOrderSummaryDetails() {
        waitForOverlayToDisappear();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        System.out.println("----- Order Details -----");

        By containerLocator = By.xpath("//div[@class='md: mt-4 flex flex-col gap-5 md:max-w-[1200px] md:flex-row md:flex-wrap md:justify-between md:gap-6 lg:mt-6 lg:gap-8 xl:gap-[72px]']");
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

        System.out.println("Order ID: " + orderId);
        System.out.println("Order Placed: " + orderPlaced);
        System.out.println("Order Status: " + orderStatus);
    } catch (Exception e) {
        System.out.println("Order has Not Placed Successfully. Closing child window");
            String currentWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();
            driver.close();

            for (String handle : allWindows) {
                if (!handle.equals(currentWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
            Assert.fail("Test Failed: Order has Not Placed Successfully");
        }
    }

    public void printDeliveryAddressDetails() {
        System.out.println("----- Delivery Info -----");

        By containerLocator = By.xpath("(//div[@class='rounded border border-lightGray p-4'])[2]");
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(containerLocator));
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

        String address = addressBuilder.toString()
                .replace(deliveryMode, "")
                .replace(phone, "")
                .trim();

        System.out.println("Delivery Mode: " + deliveryMode);
        System.out.println("Address: " + address);
        System.out.println(phone);
    }

    public void verifiesPaymentModeIframe() {
        try {
            waitForOverlayToDisappear();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Switch to iframe safely
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("HyperServices")));
            System.out.println("Switched to iframe: HyperServices");

            // Wait for key mode to appear (indicating it's fully loaded)
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'Credit') or contains(text(),'Netbanking') or contains(text(),'Wallets')]")
            ));

            // Define all expected payment mode texts (you can customize this)
            List<String> expectedModes = Arrays.asList(
                    "Credit / Debit Card", "Netbanking", "UPI", "Wallets",
                    "EMI on Cards", "Saved Cards"
            );

            Set<String> detectedModes = new LinkedHashSet<>();

            for (String mode : expectedModes) {
                List<WebElement> elements = driver.findElements(By.xpath("//*[normalize-space(text())='" + mode + "']"));
                if (!elements.isEmpty()) {
                    detectedModes.add(mode);
                }
            }
            System.out.println("----- Available Payment Modes -----");
            for (String mode : detectedModes) {
                System.out.println(mode);
            }
            System.out.println("--------------------------------------");
        } catch (Exception e) {
            System.out.println("Error while reading HyperServices iframe: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignored) {}
        }
    }

    public void printCardBlockDetails() {
        System.out.println("----- Card Block Details -----");

        By blockLocator = By.xpath("//div[@class='rounded border border-lightGray md:basis-[31%] lg:rounded-lg']");
        WebElement block = wait.until(ExpectedConditions.visibilityOfElementLocated(blockLocator));
        js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", block);

        List<WebElement> elements = block.findElements(By.xpath(".//div | .//p"));

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
            System.out.println(entry.getKey() + ": " + entry.getValue());
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
            } else {
                System.out.println("Continue Shopping button not found. Closing child window directly...");
            }
        } catch (Exception e) {
            System.out.println("Exception during Continue Shopping click: " + e.getMessage());
        } finally {
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
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("HyperServices")));
            System.out.println("Switched to HyperServices iframe");
            Thread.sleep(10);
            // Click on "Wallets"
            WebElement walletsTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(   "//*[text()='Wallets' or normalize-space()='Wallets']")));
            js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'nearest'});", walletsTab);

            walletsTab.click();
            System.out.println("Clicked on 'Wallets'");
            Thread.sleep(1000);
            // Select "PhonePe"
            WebElement phonePeOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[text()='PhonePe' or contains(text(),'Phone Pe')]")));
            phonePeOption.click();
            System.out.println("Selected 'PhonePe'");
            // Click on "Proceed"
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[text()='Proceed' or contains(text(),'Proceed')]")));
            proceedButton.click();
            System.out.println("Clicked on 'Proceed' button");
            // Switch back to main document
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("Error while selecting wallet and proceeding: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Wallet selection and proceed failed");
        }
    }

    public void rateShopping(int starCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By ratingContainer = By.xpath("//div[@data-testid='rendering-rating-component']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ratingContainer));

        By starLocator = By.xpath("//div[@data-testid='rendering-rating-component']//svg[@data-testid='rating-component-star']");
        List<WebElement> stars = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(starLocator));

        if (stars.isEmpty()) {
            throw new IllegalStateException("No star elements found.");
        }

        if (starCount < 1 || starCount > stars.size()) {
            throw new IllegalArgumentException("Invalid star count: " + starCount + ". Available: " + stars.size());
        }

        WebElement starToClick = stars.get(starCount - 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", starToClick);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", starToClick);
        System.out.println("Clicked on star: " + starCount);
    }

    public void clickElementByIndex(int indexToClick) {
        By filterLocator = By.xpath("//div[@class='cursor-pointer text-nowrap rounded-[36px] border border-neutral-300 px-2.5 py-3 md:rounded bg-transparent']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(filterLocator));

        System.out.println("Available filter options:");
        for (int i = 0; i < elements.size(); i++) {
            System.out.println(i + ": " + elements.get(i).getText().trim());
        }

        if (indexToClick >= 0 && indexToClick < elements.size()) {
            WebElement elementToClick = elements.get(indexToClick);
            wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
            System.out.println("Clicked on filter: " + elementToClick.getText().trim());
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