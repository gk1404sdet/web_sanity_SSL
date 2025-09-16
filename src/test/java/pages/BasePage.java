package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WebDriverWait fluentWait;
    public String parentWindow = "";
    public final Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        fluentWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.actions = new Actions(driver);
    }

    public WebDriverWait getFluentWait() {
        return fluentWait;
    }

    public void goTo(String url) {
        driver.get(url);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner-apm")));
    }

    public void clickOnElement(By element, int... param) {
        waitForPresenceOfElement(element);
        if (param.length == 1) {
            WebElement elementForClick = driver.findElements(element).get(param[0]);
            wait.until(ExpectedConditions.visibilityOf(elementForClick));
            elementForClick.click();
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            driver.findElement(element).click();
        }
    }

    public void enterTextOnElement(By element, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(value);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public void switchToIframe(WebElement iframe) {
        driver.switchTo().frame(iframe);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public String generateDate(String dateFormat, int dateDiff) {
        Date dt = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, dateDiff);
        dt = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setTimeZone(timeZone);
        String date = simpleDateFormat.format(dt);
        return date;
    }

    public String generateTimeInUtc(String timeFormat, int timeDiff) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, timeDiff);
        String date = simpleDateFormat.format(cal.getTime());
        return date;
    }

    public String getAttribute(By loc, String attr, int... index) {
        WebElement element;
        element = index.length > 0 ? driver.findElements(loc).get(index[0]) : driver.findElement(loc);
        return attr.equals("text") ? element.getText() : element.getAttribute(attr);
    }

    public String getDOMProperty(By loc, String attr, int... index) {
        WebElement element;
        element = index.length > 0 ? driver.findElements(loc).get(index[0]) : driver.findElement(loc);
        return wait.until(ExpectedConditions.presenceOfElementLocated(loc)).getDomProperty(attr);
    }

    public String getCssValue(By loc, String attr, int... index) {
        WebElement element;
        element = index.length > 0 ? driver.findElements(loc).get(index[0]) : driver.findElement(loc);
        return wait.until(ExpectedConditions.presenceOfElementLocated(loc)).getCssValue(attr);
    }

    public void clickWithPartialLinktext(String partialText) {
        driver.findElement(By.partialLinkText(partialText)).click();
    }

    public void verifyElementByText(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'" + text + "')]")));
        try {
            Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]")).isDisplayed());
        } catch (Exception e) {
            e.printStackTrace();
            WebElement element = driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
            element.isDisplayed();
        }
    }

    public void clickAllUsingJs(By loc) {
        for (WebElement element : driver.findElements(loc)) {
            clickJsUsingWebElement(element);
        }
    }

    public void clickJsUsingBy(By loc) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", driver.findElement(loc));
        driver.findElement(loc).click();
    }

    public void clickJsUsingWebElement(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void reloadPage() {
        driver.navigate().refresh();
    }

    public void openNewUrl(String url) {
        parentWindow = driver.getWindowHandle();
        if (url.equalsIgnoreCase("Current"))
            url = driver.getCurrentUrl();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to(url);
    }

    public void switchToParentWindow() {
        driver.switchTo().window(parentWindow);
    }

    public void pressKey(Keys key) {
        driver.findElement(By.cssSelector("body")).sendKeys(key);
    }

    public void switchBetweenTabs(int childIndex) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(childIndex));
    }

    public String getUniqueId() {
        return String.format("%s.%s", "qatest", UUID.randomUUID().toString().substring(0, 2));
    }

    public String generateMailinatorEmail(String hostName) {
        return String.format("%s@%s", getUniqueId(), hostName);
    }

    public void clickOnTextButton(String button) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'" + button + "')]")));
        clickOnElement(By.xpath("//*[contains(text(),'" + button + "')]"));
    }

    public void scrollup(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
    }

    public boolean isWebElementDisplayed(By element) {
        return driver.findElement(element).isDisplayed();
    }

    public boolean isWebElementEnabled(By element) {
        return driver.findElement(element).isEnabled();
    }

    public void waitForPresenceOfElement(By element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public void waitForInvisibilityOfElement(By element) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public void waitForURlContains(String subUrlPart) {
        wait.until(ExpectedConditions.urlContains(subUrlPart));
    }

    public boolean isElementPresent(By locatorKey) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        try {
            driver.findElement(locatorKey);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            return false;
        }
    }

    public <T> T getNestedValue(Map map, String... keys) {
        Object value = map;
        for (String key : keys) {
            value = ((Map) value).get(key);
        }
        return (T) value;
    }

    public void waitForSubUrl(String subUrl) {
        wait.until(ExpectedConditions.urlContains(subUrl));
    }

    public String replaceRandomString(String valueToBeUpdated, int noOfChar) {
        return valueToBeUpdated.replace("{random}", UUID.randomUUID().toString().substring(0, noOfChar));
    }

    public int getElementCount(By by) {
        return driver.findElements(by).size();
    }

    public void scrollDown() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "let modal = document.querySelector('div[role=\"dialog\"]');" +
                            "if(modal){ modal.scrollTop += 500; }");
            System.out.println("Scrolled...");
        } catch (Exception e) {
            System.out.println("Scroll failed: " + e.getMessage());
        }
    }

    public void moveToElement(By locator) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
        actions.moveToElement(driver.findElement(locator)).pause(Duration.ofSeconds(2)).build().perform();

    }

    public void closeChildWindowAndSwitchBack() {
        String parentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        if (allWindows.size() > 1) {
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(parentWindow)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Closing child window: " + driver.getTitle());
                    driver.close();
                }
            }
            // Switch back to parent
            driver.switchTo().window(parentWindow);
            System.out.println("Switched back to parent window: " + driver.getTitle());
        } else {
            System.out.println("No child window found. Proceeding with next step...");
        }
    }

    public void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            System.out.println("Scrolled to element: " + locator.toString());
        } catch (Exception e) {
            Assert.fail("Error while scrolling to element: " + locator.toString());
            e.printStackTrace();
        }
    }

    public void waitForOverlayToDisappear() {
        try {
            By overlayLocator = By.cssSelector("div[class*='inset-0'][class*='bg-neutral']");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        } catch (Exception e) {}
    }

    public void validateErrorMessageByPartialText(String partialText, String expectedText) {
        try {
            By errorLocator = By.xpath("//*[contains(text(),'" + partialText + "')]");
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
            String actualText = errorElement.getText().trim();

            System.out.println("Actual: " + actualText);
            Assert.assertEquals(actualText, expectedText, "Toast message mismatch!");
            System.out.println("Toasted Message is Matched: " + expectedText);
        } catch (Exception e) {
            Assert.fail("Failed to validate error message for partial text: '" + partialText + "' - " + e.getMessage());
        }
    }


}
