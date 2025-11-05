package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    // ---------- Locators ----------
    private final By login = By.xpath("//p[contains(text(),'Login')]");
//    private final By userID = By.id("Enter your phone or email ID");
    private final By userID = By.id("Enter your phone number");
    private final By proceedBtn = By.xpath("//p[contains(text(), 'PROCEED')]");
    private final By verifyOTPBtn = By.xpath("//p[contains(text(), 'VERIFY OTP')]");
    private final By hello = By.xpath("//p[contains(text(), 'Hello, ')]");
    private final By logout = By.xpath("//div[contains(text(), 'Logout')]");
    private final By yesLogout = By.xpath("//p[contains(text(), 'YES,LOG OUT')]");
    private final By resendOTP = By.xpath("//div[contains(text(), 'Resend OTP')]");
    public final By invalidFormatMsg = By.xpath("//div[contains(text(),'Invalid email or phone number format')]");
    public final By uidNotFound = By.xpath("//div[contains(text(),'Cannot find user with uid')]");
    public final By maxOtpAttempts = By.xpath("//div[contains(text(),'Since you have exceeded the maximum OTP attempts')]");
    public final By otpField = By.xpath("//div[contains(text(),'Please enter a valid OTP')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Common Actions ----------
    public void moveToHello() throws InterruptedException {
        Thread.sleep(1000);
        moveToElement(hello);
    }

    public void clickOnLogin() {
        clickOnElement(login);
    }

    public void clickOnLogout() {
        try {
            moveToHello();
            Thread.sleep(500);
            clickOnElement(logout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickOnYesButton() {
        clickOnElement(yesLogout);
    }

    public void enterUserID(String str) {
        enterTextOnElement(userID, str);
    }

    public boolean clickOnTheProceedButton() {

        if (isElementPresent(invalidFormatMsg)) {
            return false;
        }
        try {
            clickOnElement(proceedBtn);
        } catch (Exception e){
            return false;
        }
        if (isElementPresent(uidNotFound)) {
            return false;
        }
        if (isElementPresent(maxOtpAttempts)) {
            return false;
        }
        return true;
    }

    public void enterOTP(String otp) {

        waitForOverlayToDisappear();
        for(int i = 0; i < otp.length(); i++) {
            char digit = otp.charAt(i);
            String xpath = "//input[@aria-label='Please enter OTP character " + (i + 1) + "']";
            WebElement otpInput = new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            otpInput.click();
            otpInput.sendKeys(String.valueOf(digit));
        }
    }

    public boolean clickOnTheVerifyOTPButton() {
        try {
            clickOnElement(verifyOTPBtn);
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(otpField));
                return false;
            } catch (TimeoutException te) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserLoggedIn() {
        try{
            waitForOverlayToDisappear();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement welCome = wait.until(ExpectedConditions.visibilityOfElementLocated(hello));
            return welCome.isDisplayed();
        } catch (TimeoutException te) {
            return false;
        }
    }

    public void clickOnResendOTP() {
        clickOnElement(resendOTP);
    }


}
