package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AccountPage;
import pages.LoginPage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.util.List;
import java.util.Map;

public class AccountSteps {

    private final LoginPage loginPage;
    private final AccountPage accountPage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public AccountSteps(TestContext context) {

        loginPage = context.loginPage;
        accountPage = context.accountPage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    // Verify Components of Account
    @Given("user navigates to My Profile")
    public void user_navigates_to_my_profile() {
        try {
            loginPage.moveToHello();
            accountPage.moveToProfile();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Then("system should display the following components in the Account section")
    public void system_should_display_the_following_components_in_the_account_section(DataTable dataTable) throws InterruptedException {
        List<String> expectedComponents = dataTable.asList();
        List<String> actualComponents = accountPage.getAllComponentList();
        for (String expected : expectedComponents) {
            Assert.assertTrue(actualComponents.contains(expected), "Missing Component: " + expected);
            scenario.log("Verified Components: " + expected);
        }
    }

    //My Profile
    @When("user clicks on My Profile option")
    public void user_clicks_on_my_profile_option() {
        try {
            accountPage.waitForOverlayToDisappear();
            loginPage.moveToHello();
            Thread.sleep(1000);
            accountPage.clicksOnMyProfile();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @When("user updates personal details")
    public void user_updates_personal_details() {
        accountPage.clickOnEdit();
    }
    @When("user updates name")
    public void user_updates_name() {
        String name1 = configLoader.getProperty("address.firstName");
        String name2 = configLoader.getProperty("address.secondName");
        String currentName = accountPage.getEnteredName();

        if (currentName != null && currentName.equalsIgnoreCase(name1)) {
            accountPage.enterTheName(name2);
        } else {
            accountPage.enterTheName(name1);
        }
    }
    @When("user updates gender details")
    public void user_updates_gender_details() {

        accountPage.toggleGenderSelection();
    }
    @When("user clicks on Update Changes")
    public void user_clicks_on_update_changes() {

        accountPage.clickOnTheUpdateChanges();
    }
    @When("user validates that personal details are updated successfully")
    public void user_validates_that_personal_details_are_updated_successfully() {

        accountPage.validateErrorMessageByPartialText("Your profile has been updated successfully!!", "Your profile has been updated successfully!!");
    }
    @When("user adds a new address")
    public void user_adds_a_new_address() {

        accountPage.clickOnTheAddAddress();
    }
    @When("user enters new name")
    public void user_enters_new_name() {

        String name = configLoader.getProperty("address.new.name");
        accountPage.enterTheName(name);
    }
    @When("user enters new number")
    public void user_enters_new_number() {

        String mob = configLoader.getProperty("address.new.mobile");
        accountPage.enterTheMobile(mob);
    }
    @When("user enters new pin code")
    public void user_enters_new_pin_code() {

        String pin = configLoader.getProperty("address.new.pin");
        accountPage.enterThePinCode(pin);
    }
    @When("user enters new address")
    public void user_enters_new_address() {

        String add = configLoader.getProperty("address.new.line");
        accountPage.enterTheAddress( add);
    }
    @When("user selects address type as Work")
    public void user_selects_address_type_as_work() {

        accountPage.clickOnAddressType();
    }
    @When("user selects address type as Home")
    public void user_selects_a_address_type_as_home() {

        accountPage.clickOnAddressType2();
    }
    @When("user clicks on Add Address")
    public void user_clicks_on_add_address() {

        accountPage.clicksOnNewAddress();
    }
    @When("user validates that new address is added successfully")
    public void user_validates_that_new_address_is_added_successfully() {

        accountPage.validateErrorMessageByPartialText("New address has been created successfully!!", "New address has been created successfully!!");
    }
    @Then("user updates an existing address")
    public void user_updates_an_existing_address() {

        accountPage.clickOnEdit2();
    }
    @Then("user updates their name")
    public void user_updates_their_name() {

        String name = configLoader.getProperty("address.existing.name");
        accountPage.enterTheName(name);
    }
    @Then("user updates number")
    public void user_updates_number() {

        String mob = configLoader.getProperty("address.existing.mobile");
        accountPage.enterTheMobile(mob);
    }
    @Then("user updates pin code")
    public void user_updates_pin_code() {

        String pin = configLoader.getProperty("address.existing.pin");
        accountPage.enterThePinCode(pin);
    }
    @Then("user updates address")
    public void user_updates_address() {

        String add = configLoader.getProperty("address.existing.line");
        accountPage.enterTheAddress(add);
    }
    @Then("validate that existing address is updated")
    public void validate_that_existing_address_is_updated() {

        accountPage.validateErrorMessageByPartialText("Your address has been updated successfully!!", "Your address has been updated successfully!!");
    }
    @Then("user deletes existing address")
    public void user_deletes_existing_address() {

        accountPage.clicksOnDelete();
    }
    @Then("user clicks on Confirm button to delete address")
    public void user_clicks_on_confirm_button_to_delete_address() {

        accountPage.clicksOnConfirmation();
    }
    @Then("user validates that delete confirmation message is displayed")
    public void user_validates_that_delete_confirmation_message_is_displayed() {

        accountPage.validateErrorMessageByPartialText("Address deleted successfully", "Address deleted successfully");
    }

    //Book An Appointment
    @When("user clicks on Book a Personal Shopper")
    public void user_clicks_on_book_a_personal_shopper() {

        try {
            accountPage.waitForOverlayToDisappear();
            loginPage.moveToHello();
            Thread.sleep(1000);
            accountPage.clicksOnBookAPersonal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @When("user clicks on Book an Appointment")
    public void user_clicks_on_book_an_appointment() {

        accountPage.clicksOnBookAnAppointment();
    }
    @When("user selects city")
    public void user_selects_city() {

        String city = configLoader.getProperty("appointment.selectCityIndex");
        accountPage.selectCity(city);
    }
    @When("user selects store")
    public void user_selects_store() {

        accountPage.fetchAllStoreDetails(0);
    }
    @When("user validates that Get Store Directions option is displayed")
    public void user_validates_that_get_store_directions_option_is_displayed() {

        accountPage.validateErrorMessageByPartialText("Get Store Directions", "Get Store Directions");
    }
    @When("user selects date")
    public void user_selects_date() {

        accountPage.selectTomorrowDate();
    }
    @When("user clicks on Select Time")
    public void user_clicks_on_select_time() throws InterruptedException {

            accountPage.selectSlot();
    }
    @Then("user clicks on Book a Service button")
    public void user_clicks_on_book_a_service_button() {

        accountPage.clicksOnBookAService();
    }
    @Then("user validates personal details")
    public void user_validates_personal_details() {

        accountPage.verifyBookingSummary();
    }
    @Then("user clicks on Proceed to Book button")
    public void user_clicks_on_proceed_to_book_button() {

        accountPage.clicksOnProceedToBook();
    }
    @Then("user validates that booking confirmation is displayed")
    public void user_validates_that_booking_confirmation_is_displayed() {

        accountPage.validateErrorMessageByPartialText("Your Appointment has booked successfully", "Your Appointment has booked successfully");
    }

    //My Orders
    @Given("user clicks on My Orders")
    public void user_clicks_on_my_orders() {

        try {
            accountPage.waitForOverlayToDisappear();
            loginPage.moveToHello();
            Thread.sleep(1000);
            accountPage.clicksOnMyOrders();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @When("user views existing orders")
    public void user_views_existing_orders() {

        accountPage.printAllProductDetailsAcrossPages();
    }
    @When("user applies filter option")
    public void user_applies_filter_option() {

        accountPage.clickOnTheFilter();
    }
    @When("user applies filter Past {int} Days")
    public void user_applies_filter_past_days(Integer mon) {

        String filter = configLoader.getProperty("filter.option1");
        accountPage.applyFilterOption(filter);
    }
    @Then("user views filtered orders accordingly")
    public void user_views_filtered_orders_accordingly() {

        accountPage.printAllProductDetailsAcrossPages();
    }
    @When("user applies filter Past {int} Months")
    public void user_applies_filter_past_months(Integer mon) {

        String filter = configLoader.getProperty("filter.option2");
        accountPage.applyFilterOption(filter);
    }
    @When("user applies filter Completed")
    public void user_applies_filter_completed() {

        String filter = configLoader.getProperty("filter.option3");
        accountPage.applyFilterOption(filter);
    }
    @When("user applies filter Cancelled")
    public void user_applies_filter_cancelled() {

        String filter = configLoader.getProperty("filter.option4");
        accountPage.applyFilterOption(filter);
    }
    @When("user applies filter Past {int} Year")
    public void user_applies_filter_past_year(Integer mon) {

        String filter = configLoader.getProperty("filter.option5");
        accountPage.applyFilterOption(filter);
    }
    @When("user clicks on an existing order to view details")
    public void user_clicks_on_an_existing_order_to_view_details() {

        accountPage.clickFlexDivByIndex(2);
    }
    @Then("user validates order details successfully")
    public void user_validates_order_details_successfully() {

        accountPage.verifyOrderedProductSummary();
    }

    //My Wallet
    @When("user clicks on My Wallet")
    public void user_clicks_on_my_wallet() {

        try {
            accountPage.waitForOverlayToDisappear();
            loginPage.moveToHello();
            Thread.sleep(1000);
            accountPage.clicksOnMyWallet();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @When("user validates wallet account details")
    public void user_validates_wallet_account_details() {

        Map<String, String> summary = accountPage.printWalletSummaryData();
        summary.forEach((k, v) -> scenario.log(k + ": " + v));
    }

    //First Citizen Club
    @When("user clicks on First Citizen Club")
    public void user_clicks_on_first_citizen_club() {

        try {
            accountPage.waitForOverlayToDisappear();
            loginPage.moveToHello();
            Thread.sleep(1000);
            accountPage.clicksOnFirstCitizenClub();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @When("system should display the following components in the FCC section")
    public void system_should_display_the_following_components_in_the_fcc_section(DataTable dataTable) {

        List<String> expectedSections = dataTable.asList();
        boolean result = accountPage.validateFirstCitizenClubSections(expectedSections);
        Assert.assertTrue(result, "Some expected components are missing on the First Citizen Club page!");

    }
    @Then("user clicks on Card")
    public void user_clicks_on_card() {
        accountPage.clickOnCard();
    }
    @Then("user validates card details")
    public void user_validates_card_details() {

        Map<String, String> cardInfo =accountPage.getCardNumberAndType();

        String cardNumber = cardInfo.get("Card Number");
        String cardType = cardInfo.get("Card type");
        String primaryFCCPoints = cardInfo.get("First Citizen Club Primary Points");
        String secondaryFCCPoints = cardInfo.get("First Citizen Club Secondary Points");

        scenario.log("Card Number: " + cardNumber);
        scenario.log("Card Type: " + cardType);
        scenario.log("First Citizen Club Primary Points: " + primaryFCCPoints);
        scenario.log("First Citizen Club Secondary Points: " + secondaryFCCPoints);

        Assert.assertTrue(cardNumber != null && !cardNumber.isEmpty(), "Card Number is empty or missing");
        Assert.assertTrue(cardType != null && !cardType.isEmpty(), "Card Type is empty or missing");

    }

    //Gift or EGV
    @When("user clicks on My Gift Card or EGV")
    public void user_clicks_on_my_gift_card_or_egv() {

        try {
            accountPage.waitForOverlayToDisappear();
            loginPage.moveToHello();
            Thread.sleep(1000);
            accountPage.clicksOnGiftCard();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @When("user validates gift voucher product list")
    public void user_validates_gift_voucher_product_list() {

        int totalItems = accountPage.countGridItems();
        scenario.log(" " + totalItems);
    }
}
