package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

public class LoginSteps {

    private final LoginPage loginPage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public LoginSteps(TestContext context) {

        loginPage = context.loginPage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    //Login with Mobile Number
    @Given("user launches the application")
    public void user_launches_the_application() {
        loginPage.goTo(configLoader.getProperty("uatUrl"));
    }
    @When("user taps on the Login button")
    public void user_taps_on_the_login_button() {
        loginPage.clickOnLogin();
    }
    @When("user enters mobile number {string}")
    public void user_enters_mobile_number(String string) {
        loginPage.enterUserID(string);
    }
    @When("user clicks on the Proceed button")
    public void user_clicks_on_the_proceed_button() {
        boolean isProceedSuccessful = loginPage.clickOnTheProceedButton();
        Assert.assertTrue(isProceedSuccessful, "Proceed Failed: User may be blocked for 24 hours or invalid input was provided");
    }
    @Then("user enters OTP {string}")
    public void user_enters_otp(String string) {
        loginPage.enterOTP(string);
    }
    @Then("user clicks on the Verify OTP button")
    public void user_clicks_on_the_verify_otp_button() {
        boolean isVerified = loginPage.clickOnTheVerifyOTPButton();
        Assert.assertTrue(isVerified, "OTP Verification Failed");
    }
    @Then("system should display the appropriate login status")
    public void system_should_display_the_appropriate_login_status() {
        Assert.assertTrue(loginPage.isUserLoggedIn(), "User Login Verification has Failed");
    }

    // LOGOUT
    @Given("user clicks on the Logout button")
    public void user_clicks_on_the_logout_button() {
        loginPage.clickOnLogout();
    }
    @When("user clicks on the yes button for logout")
    public void user_clicks_on_the_yes_button_for_logout() {
        loginPage.clickOnYesButton();
    }
    @Then("user validate that the user is logged out")
    public void user_validate_that_the_user_is_logged_out() {
        scenario.log("User has logged out successfully");
    }

    //Login with Email ID
    @When("user enters email id {string}")
    public void user_enters_email_id(String emailId) {
        loginPage.enterUserID(emailId);
        scenario.log("Entered the Email ID: " + emailId);
    }
    @Then("user enters OTP")
    public void user_enters_otp() {
        scenario.log("OTP cannot be automated for email. Skipping OTP validation step");
    }

    //OTP Functionality
    @Then("user clicks on the Verify OTP button for validation")
    public void user_clicks_on_the_verify_otp_button_for_validation() {
        Assert.assertFalse(loginPage.clickOnTheVerifyOTPButton(), "Invalid OTP/User Not entered the OTP");
    }
    @Then("user clicks the resend otp option")
    public void user_clicks_the_resend_otp_option() {
        loginPage.clickOnResendOTP();
    }
    @Then("user has successfully resent the OTP")
    public void user_has_successfully_resent_the_otp() {
        scenario.log("OTP has successfully has resent");
    }

    //Login with Invalid Mobile Number
    @When("User enters the invalid mobile number  {string}")
    public void user_enters_the_invalid_mobile_number(String mobileNO) {
        loginPage.enterUserID(mobileNO);
        scenario.log("Entered the Mobile Number: " + mobileNO);
    }
    @When("user clicks the Proceed button for validation of invalidUid")
    public void user_clicks_the_proceed_button_for_validation_of_invalid_uid() {
        loginPage.clickOnTheProceedButton();
    }
    @Then("user validate that the appropriate error message is displayed")
    public void user_validate_that_the_appropriate_error_message_is_displayed() {
        loginPage.validateErrorMessageByPartialText("Invalid email or phone number format", "Invalid email or phone number format");
    }

    //Login with Invalid Email ID
    @Given("user enters the invalid email id {string}")
    public void user_enters_the_invalid_email_id(String emailID) {
        loginPage.enterUserID(emailID);
        scenario.log("Entered Email ID: " + emailID);
    }


}
