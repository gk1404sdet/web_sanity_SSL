package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.util.List;

public class AccountSteps {

    private final LoginPage loginPage;
    private final AccountPage accountPage;
    private final HomePage homePage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public AccountSteps(TestContext context) {

        loginPage = context.loginPage;
        accountPage = context.accountPage;
        homePage = context.homePage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }
    // Verify Components of Account
    @Given("user navigation to my profile")
    public void user_navigation_to_my_profile() {
        loginPage.moveToHello();
        accountPage.moveToElement(accountPage.profile);
    }
    @When("system should display the following components in the Account section")
    public void system_should_display_the_following_components_in_the_account_section(DataTable dataTable) {
        List<String> expectedComponents = dataTable.asList();
        List<String> actualComponents = accountPage.getAllComponentList();
        for (String expected : expectedComponents) {
            Assert.assertTrue(actualComponents.contains(expected), "Missing Component: " + expected);
            System.out.println("Verified Components: " + expected);
        }
    }

    //My Profile
    @Given("user clicks on the my Profile option")
    public void user_clicks_on_the_my_profile_option() {
        loginPage.moveToHello();
        accountPage.clicksOnMyProfile();
    }
    @When("user updates their personal details")
    public void user_updates_their_personal_details() {
        accountPage.clickOnTheEdit(true);
    }
    @When("user update their name")
    public void user_update_their_name() {
        accountPage.enterTextOnElement(accountPage.name, "Gopalakrishnan Palanisamy");
    }
    @When("user update their gender details")
    public void user_update_their_gender_details() {
        accountPage.toggleGenderSelection();
    }
    @When("user clicks on the update changes")
    public void user_clicks_on_the_update_changes() {
        accountPage.clickOnTheUpdateChanges();
    }
    @When("user validate that personal details successfully updated")
    public void user_validate_that_personal_details_successfully_updated() {
        accountPage.verifyElementByText("Your profile has been updated successfully!!");
    }
    @When("user is able to add a new address")
    public void user_is_able_to_add_a_new_address() {
        accountPage.clickOnTheAddAddress();
    }
    @When("user enters the new name")
    public void user_enters_the_new_name() {
        accountPage.enterTextOnElement(accountPage.name, "Shoppersstop Testing");
    }
    @When("user enters the new number")
    public void user_enters_the_new_number() {
        accountPage.enterTextOnElement(accountPage.mobNo, "9790153971");
    }
    @When("user enters the new pin code")
    public void user_enters_the_new_pin_code() {
        accountPage.enterTextOnElement(accountPage.pinCode, "638056");
    }
    @When("user enters the new address")
    public void user_enters_the_new_address() {
        accountPage.enterTextOnElement(accountPage.address, "Jayanagar, Bengaluru");
    }
    @When("user selects a address type as work")
    public void user_selects_a_address_type_as_work() {
        accountPage.clickJsUsingBy(accountPage.addressType);
    }
    @When("user clicks on the add address")
    public void user_clicks_on_the_add_address() {
        accountPage.clickOnElement(accountPage.newAddAddress);
    }
    @When("user validate that new address added successfully")
    public void user_validate_that_new_address_added_successfully() {
        accountPage.verifyElementByText("New address has been created successfully!!");
    }
    @Then("user updates an existing address")
    public void user_updates_an_existing_address() {
        accountPage.clickOnTheEdit(false);
    }
    @Then("user updates their name")
    public void user_updates_their_name() {
        accountPage.enterTextOnElement(accountPage.name, "Moolya Testing Software Pvt Ltd");
    }
    @Then("user updates their number")
    public void user_updates_their_number() {
        accountPage.enterTextOnElement(accountPage.mobNo,"9790153970");
    }
    @Then("user updates their pin code")
    public void user_updates_their_pin_code() {
        accountPage.enterTextOnElement(accountPage.pinCode, "638052");
    }
    @Then("user updates their address")
    public void user_updates_their_address() {
        accountPage.enterTextOnElement(accountPage.address, "Basavanakudi, Bengaluru");
    }
    @Then("validate that existing address has updated")
    public void validate_that_existing_address_has_updated() {
        accountPage.verifyElementByText("Your address has been updated successfully!!");
    }
    @Then("user is able to delete exiting address")
    public void user_is_able_to_delete_exiting_address() {
        accountPage.clicksOnDelete();
    }
    @Then("user clicks on the confirm button for delete address")
    public void user_clicks_on_the_confirm_button_for_delete_address() {
        accountPage.clicksOnConfirmation();
    }
    @Then("user validate that delete address message is displayed")
    public void user_validate_that_delete_address_message_is_displayed() {
        accountPage.verifyElementByText("Address deleted successfully");
    }

    //Book An Appointment
    @When("user clicks on Book A Personal Shopper")
    public void user_clicks_on_book_a_personal_shopper() {
        loginPage.moveToHello();
        accountPage.clickOnElement(accountPage.bookAPersonalShopper);
    }
    @When("user clicks on the book an appointment")
    public void user_clicks_on_the_book_an_appointment() {
        accountPage.clicksOnBookAnAppointment();
    }
    @When("user selects the city")
    public void user_selects_the_city() {
        accountPage.selectCity("8");
    }
    @When("user selects the store")
    public void user_selects_the_store() {
        accountPage.fetchAllStoreDetails();
    }
    @When("user validate that get store directions option is displayed")
    public void user_validate_that_get_store_directions_option_is_displayed() {
        accountPage.verifyElementByText("Get Store Directions");
    }
    @When("user select the date")
    public void user_select_the_date() {
        accountPage.selectTomorrowDate();
    }
    @When("user clicks on the select time")
    public void user_clicks_on_the_select_time() {
        accountPage.selectSlot();
    }
    @Then("user clicks on the book a service button")
    public void user_clicks_on_the_book_a_service_button() {
        accountPage.clicksOnBookAService();
    }
    @Then("user validate that personal details")
    public void user_validate_that_personal_details() {
        accountPage.verifyBookingSummary();
    }
    @Then("user clicks on the proceed to book button")
    public void user_clicks_on_the_proceed_to_book_button() {
        accountPage.clicksOnProceedToBook();
    }
    @Then("user validate that booking confirmation is displayed")
    public void user_validate_that_booking_confirmation_is_displayed() {
        accountPage.verifyElementByText("Your Appointment has booked successfully");
    }

    //My Orders
    @Given("user clicks on My Orders")
    public void user_clicks_on_my_orders() {
        loginPage.moveToHello();
        accountPage.clicksOnMyOrders();
    }
    @When("user is able to see the existing orders")
    public void user_is_able_to_see_the_existing_orders() {
        accountPage.printAllProductDetailsAcrossPages();
    }
    @When("user applies the filter option")
    public void user_applies_the_filter_option() {
        accountPage.clickOnTheFilter();
    }
    @When("user applies filter Past {int} days")
    public void user_applies_filter_past_days(Integer int1) {
        accountPage.applyFilterOption("Past 30 Days");
    }
    @Then("user is able to see the filtered orders accordingly")
    public void user_is_able_to_see_the_filtered_orders_accordingly() {
        accountPage.printAllProductDetailsAcrossPages();
    }
    @When("user applies filter Past {int} Months")
    public void user_applies_filter_past_months(Integer int1) {
        accountPage.applyFilterOption("Past 6 Months");
    }
    @When("user applies filter Completed")
    public void user_applies_filter_completed() {
       accountPage.applyFilterOption("Completed");
    }
    @When("user applies filter Cancelled")
    public void user_applies_filter_cancelled() {
        accountPage.applyFilterOption("Cancelled");
    }
    @When("user applies filter Past {int} Year")
    public void user_applies_filter_past_year(Integer int1) {
        accountPage.applyFilterOption("Past 1 Year");
    }
    @When("user clicks on an existing order to view details")
    public void user_clicks_on_an_existing_order_to_view_details() {
        accountPage.clickFlexDivByIndex(2);
    }
    @Then("user validates the order details successfully")
    public void user_validates_the_order_details_successfully() {
        accountPage.verifyOrderedProductSummary();
    }

    //My Wallet
    @When("user clicks the my Wallet")
    public void user_clicks_the_my_wallet() {
        loginPage.moveToHello();
        accountPage.clicksOnMyWallet();
    }
    @When("user validates that wallet account details")
    public void user_validates_that_wallet_account_details() {
        accountPage.printWalletSummaryData();
    }

    //Gift or EGV
    @When("user clicks the My Gift card or EGV")
    public void user_clicks_the_my_gift_card_or_egv() {
        accountPage.clicksOnGiftCard();
    }
    @When("user validates that gift vouchers product list")
    public void user_validates_that_gift_vouchers_product_list() {
        accountPage.countGridItems();
    }
}
