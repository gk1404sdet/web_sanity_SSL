package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WebDriverWait fluentWait;
    public final Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        fluentWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.actions = new Actions(driver);
    }

    public void goTo(String url) {
        driver.get(url);
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

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void clickJsUsingBy(By loc) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", driver.findElement(loc));
        driver.findElement(loc).click();
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

    public void waitForPresenceOfElement(By element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
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

    public void scrollDown() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "let modal = document.querySelector('div[role=\"dialog\"]');" +
                            "if(modal){ modal.scrollTop += 500; }");
            } catch (Exception e) {}
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
                    driver.close();
                }
            }
            // Switch back to parent
            driver.switchTo().window(parentWindow);
        } else {}
    }

    public void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        } catch (Exception e) {
            Assert.fail("Error while scrolling to element: " + locator.toString());
            e.printStackTrace();
        }
    }

    public void waitForOverlayToDisappear() {
        try {
            By overlayLocator = By.cssSelector("div[class*='inset-0'][class*='bg-neutral']");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        } catch (Exception e) {}
    }

    public void validateErrorMessageByPartialText(String partialText, String expectedText) {
        try {
            By errorLocator = By.xpath("//*[contains(text(),'" + partialText + "')]");
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
            String actualText = errorElement.getText().trim();

            Assert.assertEquals(actualText, expectedText, "Toast message mismatch!");
        } catch (Exception e) {
            Assert.fail("Failed to validate error message for partial text: '" + partialText + "' - " + e.getMessage());
        }
    }
}
