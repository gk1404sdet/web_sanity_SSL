package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By login = By.xpath("//p[contains(text(),'Login')]");
    private final By userID = By.id("Enter your phone or email ID");
    private final By proceedBtn = By.xpath("//p[contains(text(), 'PROCEED')]");
    private final By verifyOTPBtn = By.xpath("//p[contains(text(), 'VERIFY OTP')]");
    private final By hello = By.xpath("//p[contains(text(), 'Hello,')]");
    private final By logout = By.xpath("//div[contains(text(), 'Logout')]");
    private final By yesLogout = By.xpath("//p[contains(text(), 'YES,LOG OUT')]");
    private final By resendOTP = By.xpath("//div[contains(text(), 'Resend OTP')]");

    public void moveToHello() {
        moveToElement(hello);
    }

    public void clickOnLogin() {
        clickOnElement(login);
    }

    public void clickOnLogout() {
        moveToHello();
        clickOnElement(logout);
    }

    public void clickOnYesButton() {
        clickOnElement(yesLogout);
    }

    public void enterUserID(String str) {
        enterTextOnElement(userID, str);
    }

    public boolean clickOnTheProceedButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (isElementPresent(By.xpath("//div[contains(text(),'Invalid email or phone number format')]"))) {
            System.out.println("Test failed: Invalid email or phone number format");
            return false;
        }
        try {
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(proceedBtn));
            proceedButton.click();
        } catch (Exception e){
            System.out.println("Proceed button was not clickable: " + e.getMessage());
            return false;
        }
        if (isElementPresent(By.xpath("//div[contains(text(),'Cannot find user with uid')]"))) {
            System.out.println("Test failed: Cannot find user with UID");
            return false;
        }
        if (isElementPresent(By.xpath("//div[contains(text(),'Since you have exceeded the maximum OTP attempts')]"))) {
            System.out.println("Test failed: Maximum OTP attempts exceeded. Number locked for 24 hours.");
            return false;
        }
        System.out.println("Proceed operation completed successfully.");
        return true;
    }

    public void enterOTP(String otp) {
        for(int i = 0; i < otp.length(); i++) {
            char digit = otp.charAt(i);
            String xpath = "//input[@aria-label='Please enter OTP character " + (i + 1) + "']";
            WebElement otpInput = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            otpInput.click();
            otpInput.sendKeys(String.valueOf(digit));
        }
    }

    public boolean clickOnTheVerifyOTPButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement verifyOtpButton = wait.until(ExpectedConditions.elementToBeClickable(verifyOTPBtn));
            verifyOtpButton.click();
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),'Please enter a valid OTP')]")
                ));
                System.out.println("Test failed: Invalid OTP entered");
                return false;
            } catch (TimeoutException te) {
                System.out.println("OTP validated successfully. Proceeding...");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception while clicking Verify OTP: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserLoggedIn() {
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement welCome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Hello, ')]")));
            return welCome.isDisplayed();
        } catch (TimeoutException te) {
            System.out.println("Login Failed: Post-Login Element not found");
            return false;
        }
    }

    public void clickOnResendOTP() {
        clickOnElement(resendOTP);
    }


}
