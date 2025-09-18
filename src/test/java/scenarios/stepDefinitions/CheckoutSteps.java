package scenarios.stepDefinitions;


import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.*;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.util.List;
import java.util.Map;

import static factory.DriverFactory.driver;


public class CheckoutSteps {

    boolean productAdded = false;
    private final CheckoutPage checkoutPage;
    private final CartPage cartPage;
    private final HomePage homePage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public CheckoutSteps(TestContext context) {

        checkoutPage = context.checkoutPage;
        cartPage = context.cartPage;
        homePage = context.homePage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    @Given("user clicks on the Men category")
    public void user_clicks_on_the_men_category() {
        homePage.moveToCategory(homePage.men);
    }

    @When("user selects a sub-category under Men")
    public void user_selects_a_sub_category_under_men() {
        homePage.clickOnSubCategory(homePage.formalShoes);
    }

    @When("user selects a product from the PLP")
    public void user_selects_a_product_from_the_plp() {
        checkoutPage.waitForOverlayToDisappear();
        boolean result = checkoutPage.clickProductByIndex(checkoutPage.productList, 2);
        if (!result) {
            Assert.fail("Could not click product at index 2 from PLP");
        } else {
            scenario.log("Product clicked successfully at index 2");
        }
    }

    @When("user validates all product detail section components are displayed")
    public void user_validates_all_product_detail_section_components_are_displayed(DataTable dataTable) {
        List<String> expectedSections = dataTable.asList();
        boolean result = homePage.validateProductDetailSections(expectedSections);
        Assert.assertTrue(result, "Some expected components are missing on the PDP page!");
    }

    @Then("user is navigated to the product detail page")
    public void user_is_navigated_to_the_product_detail_page() {
        Map<String, String> details = checkoutPage.fetchPDPDetails();

        if (details.containsKey("error")) {
            Assert.fail(details.get("error"));
        } else {
            scenario.log("PDP Details:");
            scenario.log("Brand: " + details.get("brand") + "Title; " + details.get("title") );
            scenario.log("Title: " + details.get("title"));
            scenario.log("Price: " + details.get("price"));
            scenario.log("MRP: " + details.get("mrp"));
            scenario.log("Discount: " + details.get("discount"));
        }
    }

    @Then("user validating the out of stock product")
    public void user_validating_the_out_of_stock_product() {
        if(checkoutPage.isOutOfStockDisplayed()) {
            scenario.log("Product is OUT OF STOCK. Retrying product selection...");
            for (String handle : driver.getWindowHandles()) {}
            driver.close();
            try {
                user_clicks_on_the_men_category();
                user_selects_a_sub_category_under_men();
                checkoutPage.clickProductByIndex(checkoutPage.productList, 2);
            } catch (Exception e) {}
        }
        else {
            scenario.log(" Product is in stock. Proceeding");
        }
    }

    @When("user selects a size if available")
    public void user_selects_a_size_if_available() {
        try {
            boolean sizes = checkoutPage.selectSize();
            if (!sizes) {
                scenario.log("Product does not require size selection. Proceeding");
            }
        } catch (Exception e) {
            Assert.fail("Error during size selection: " + e.getMessage());
        }
    }

    @When("user clicks on Add to Bag and View Bag")
    public void user_clicks_on_add_to_bag_and_view_bag() {
       checkoutPage.clickAddToBagOrViewBagFallback();
    }

    @Then("user clicks on the View Bag")
    public void user_clicks_on_the_view_bag() {
        if(!cartPage.isCartPage()) {
            cartPage.clicksOnTheViewBag();
        } else {
            scenario.log("Already on cart page. Skipping View Bag click.");
        }
    }

    @Then("user validates the price details in the bag")
    public void user_validates_the_price_details_in_the_bag() {
       cartPage.emptyBagValidation();
    }

    @When("user clicks on Place Order")
    public void user_clicks_on_place_order() {
        checkoutPage.clickOnThePlaceOrder();
    }

    @When("user selects the delivery address")
    public void user_selects_the_delivery_address() throws InterruptedException {
        Thread.sleep(20);
        checkoutPage.selectTheAddress();
    }

    @When("user clicks the Continue button")
    public void user_clicks_the_continue_button() {
        checkoutPage.clickOnContinueButton();
    }

    @When("user verifies the available payment modes")
    public void user_verifies_the_available_payment_modes () {
        List<String> modes = checkoutPage.verifiesPaymentModeIframe();

        for (String mode : modes) {
            scenario.log("Detected mode: " + mode);
        }
    }

    @Then("user checks if Cash on Delivery is available")
    public void user_checks_if_cash_on_delivery_is_available() {
        checkoutPage.selectingTheCOD();
    }

    // Order Summary Page
    @Given("user validating successful order summary")
    public void user_validating_successful_order_summary() {
        checkoutPage.printOrderSummaryDetails();
    }

    @When("user validating the Deliver Mode and Address")
    public void user_validating_the_deliver_mode_and_address() {
        checkoutPage.printDeliveryAddressDetails();
    }

    @Then("user validates the final price details")
    public void user_validates_the_final_price_details() {
        checkoutPage.printCardBlockDetails();
    }

    // Review Shopping
    @When("user verifying the review shopping experience")
    public void user_verifying_the_review_shopping_experience() {
        checkoutPage.scrollToReview();
    }

    @When("user giving the rate for shopping")
    public void user_giving_the_rate_for_shopping() {
        checkoutPage.rateShopping();
    }

    @When("user selecting the what can be improved option")
    public void user_selecting_the_what_can_be_improved_option() {
        checkoutPage.clickElementByIndex(3);
    }

    @When("user submit the review")
    public void user_submit_the_review() {
        checkoutPage.clickOnSubmitButton();
    }

    @Then("user clicks the Continue Shopping Button")
    public void user_clicks_the_continue_shopping_button() {
        checkoutPage.clickOnTheContinueShoppingOrClose();
    }

    // Failed payment
    @Then("user selecting the Wallet Option")
    public void user_selecting_the_wallet_option() {
        checkoutPage.selectWalletAndProceed();
    }

    @Then("system should display the Something Went Wrong Message")
    public void system_should_display_the_something_went_wrong_message() {
        checkoutPage.validateErrorMessageByPartialText("Something went wrong. Please try again!","Something went wrong. Please try again!");
    }

    @Then("user back to Home Page")
    public void user_back_to_home_page() {
        checkoutPage.closeChildWindowAndSwitchBack();
        homePage.clickHomeLinkIfAvailable();
    }
}

