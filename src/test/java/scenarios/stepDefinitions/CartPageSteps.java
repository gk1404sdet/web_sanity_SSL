package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

public class CartPageSteps {

    private final CartPage cartPage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public CartPageSteps(TestContext context) {

        cartPage = context.cartPage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    // Cart Page Components Verification
    @When("user clicks on the Cart icon")
    public void user_clicks_on_the_cart_icon() {
        cartPage.clickOnCartIcon();
    }
    @When("system should display the following components in the cart page")
    public void system_should_display_the_following_components_in_the_cart_page(DataTable dataTable) {
        cartPage.validateCartComponentsByText();
    }

    // Pincode check on the cart page
    @Given("user clicks the pincode check box")
    public void user_clicks_the_pincode_check_box() {
        cartPage.clickOnPincodeBox();
    }
    @When("user enters the invalid pincode")
    public void user_enters_the_invalid_pincode() {
        cartPage.enterThePincode( "086245");
    }
    @When("user enters the pincode")
    public void user_enters_the_pincode() {
        cartPage.enterThePincode( "560076");
    }
    @Then("user clicks the check option to validate the pincode")
    public void user_clicks_the_check_option_to_validate_the_pincode() {
        cartPage.clickOnCheckOption();
    }
    @Then("system should display the appropriate error status")
    public void system_should_display_the_appropriate_error_status() {
        cartPage.verifyElementByText("*Pincode is not serviceable");
    }
    @Then("system should display the appropriate error message")
    public void system_should_display_the_appropriate_error_message() {
        cartPage.verifyElementByText("*Please enter a valid pincode");
    }

    // Size - Cart
    @Given("user clicks the size changing option")
    public void user_clicks_the_size_changing_option() {
        cartPage.clicksOnTheSizeChange(1);
    }
    @When("user validate that available size")
    public void user_validate_that_available_size() {
        cartPage.clicksOnTheSizeChart();
    }

    // Increase, Decrease the Quantity
    @When("user increase the quantity from the cart")
    public void user_increase_the_quantity_from_the_cart() {
        cartPage.adjustQuantity(true, 3);
    }
    @Then("user decrease the quantity from the cart")
    public void user_decrease_the_quantity_from_the_cart() {
        cartPage.adjustQuantity(false, 2);
    }

    // Gift Wrap
    @When("user clicks gift wrap option")
    public void user_clicks_gift_wrap_option() {
        cartPage.clickOnGiftWrap();
    }
    @When("user enters the receiver's name")
    public void user_enters_the_receiver_s_name() {
        cartPage.enterTextOnElement(cartPage.receiverName, "Moolya Software Testing");
    }
    @When("user enters the gift message")
    public void user_enters_the_gift_message() {
        cartPage.enterTextOnElement(cartPage.message, "Happy Birthday.....");
    }
    @When("user enters the sender's name")
    public void user_enters_the_sender_s_name() {
        cartPage.enterTextOnElement(cartPage.senderName, "May God bless you");
    }
    @Then("user clicks on the save gift message")
    public void user_clicks_on_the_save_gift_message() {
        cartPage.clickOnSaveGiftWrap();
    }
    @Then("user validate that gift wrap details added successfully")
    public void user_validate_that_gift_wrap_details_added_successfully() throws InterruptedException {
        cartPage.verifyElementByText("Gift wrap added to cart");
        Thread.sleep(2000);
    }
    @Then("user validate that gift wrap details removed successfully")
    public void user_validate_that_gift_wrap_details_removed_successfully() {
        cartPage.verifyElementByText("Gift wrap removed from cart");
    }


    // Move to Wishlist and Remove - cart
    @When("user clicks on the xmark icon")
    public void user_clicks_on_the_xmark_icon() {
        cartPage.clickOnXmark();
    }
    @When("user is moving the product to wishlist from bag")
    public void user_is_moving_the_product_to_wishlist_from_bag() {
        cartPage.clickOnTheMoveToWishlist();
    }
    @When("user validates that product moved to wishlist from bag")
    public void user_validates_that_product_moved_to_wishlist_from_bag() {
        cartPage.verifyElementByText("Product removed from your bag and added to your wishlist");
    }
    @Then("user is removing the product from bag")
    public void user_is_removing_the_product_from_bag() {
        cartPage.clickOnRemove();
    }
    @Then("user validates that product removed from bag")
    public void user_validates_that_product_removed_from_bag() {
        cartPage.verifyElementByText("Product removed from your cart successfully");
    }

}
