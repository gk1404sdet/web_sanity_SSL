package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CartPage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.util.List;

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

    // CRUD Operation
    @When("user clicks on the add new address")
    public void user_clicks_on_the_add_new_address() {

        cartPage.clickOnAddAddress();
    }

    // Cart Page Components Verification
    @When("user clicks on the Cart icon")
    public void user_clicks_on_the_cart_icon() {

        cartPage.clickOnCartIcon();
    }
    @When("system should display the following components on the cart page")
    public void system_should_display_the_following_components_on_the_cart_page(DataTable dataTable) {

        List<String> expectedSections = dataTable.asList();
       boolean result = cartPage.validateCartComponentsByText(expectedSections);
        Assert.assertTrue(result, "Some expected components are missing on the cart page!");
    }

    // Pincode check on the cart page
    @Given("user clicks on the postal code check box")
    public void user_clicks_on_the_postal_code_check_box() {

        cartPage.clickOnPincodeBox();
    }
    @When("user enters an invalid postal code")
    public void user_enters_an_invalid_postal_code() {

        cartPage.enterThePincode( "08624");
    }
    @When("user enters the postal code")
    public void user_enters_the_postal_code() {

        cartPage.enterThePincode( "560076");
    }
    @Then("user clicks on the check option to validate the postal code")
    public void user_clicks_on_the_check_option_to_validate_the_postal_code() {

        cartPage.clickOnCheckOption();
    }
    @Then("system should display the appropriate error message")
    public void system_should_display_the_appropriate_error_message() {

        cartPage.validateErrorMessageByPartialText("*Please enter a valid pincode", "*Please enter a valid pincode");
    }

    // Increase, Decrease the Quantity
    @When("user increases the quantity from the cart")
    public void user_increases_the_quantity_from_the_cart() {

        cartPage.adjustQuantity(true, 3);
    }
    @Then("user decreases the quantity from the cart")
    public void user_decreases_the_quantity_from_the_cart() {

        cartPage.adjustQuantity(false, 2);
    }

    // Gift Wrap
    @When("user clicks on the Gift Wrap option")
    public void user_clicks_on_the_gift_wrap_option() {

        cartPage.scrollup(0,0);
        cartPage.clickOnGiftWrap();
    }
    @When("user enters the receiver's name")
    public void user_enters_the_receiver_s_name() {

        cartPage.enterTheReceiverName("Moolya Software Testing");
    }
    @When("user enters the gift message")
    public void user_enters_the_gift_message() {

        cartPage.enterTheMessage("Happy Birthday.....");
    }
    @When("user enters the sender's name")
    public void user_enters_the_sender_s_name() {

        cartPage.enterTheSenderName("May God bless you");
    }
    @Then("user clicks on save gift message")
    public void user_clicks_on_save_gift_message() {

        cartPage.clickOnSaveGiftWrap();
    }
    @Then("user validates that Gift Wrap details added successfully")
    public void user_validates_that_gift_wrap_details_added_successfully() throws InterruptedException {

        cartPage.validateErrorMessageByPartialText("Gift wrap added to cart", "Gift wrap added to cart");
        Thread.sleep(2000);
    }
    @Then("user validates that Gift Wrap details removed successfully")
    public void user_validates_that_gift_wrap_details_removed_successfully() {

        cartPage.validateErrorMessageByPartialText("Gift wrap removed from cart", "Gift wrap removed from cart");
    }

    // Size - Cart
    @Given("user clicks on the size change option")
    public void user_clicks_on_the_size_change_option() {

        cartPage.clicksOnTheSizeChange(0);
    }
    @When("user validates that available size is displayed")
    public void user_validates_that_available_size_is_displayed() {

        cartPage.clicksOnTheSizeChart();
    }

    // Move to Wishlist and Remove - cart
    @When("user clicks on the X-mark icon")
    public void user_clicks_on_the_x_mark_icon() {

        cartPage.clickOnXmark();
    }
    @When("user moves the product to wishlist from bag")
    public void user_moves_the_product_to_wishlist_from_bag() {

        cartPage.clickOnTheMoveToWishlist();
    }
    @Then("user removes the product from bag")
    public void user_removes_the_product_from_bag() {

        cartPage.clickOnRemove();
    }
    @Then("user validates that product is removed from bag")
    public void user_validates_that_product_is_removed_from_bag() {

        cartPage.validateErrorMessageByPartialText("Product removed from your cart successfully", "Product removed from your cart successfully");
    }
}
