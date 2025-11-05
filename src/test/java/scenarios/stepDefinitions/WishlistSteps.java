package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CheckoutPage;
import pages.HomePage;
import pages.WishlistPage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

public class WishlistSteps {

    private final WishlistPage wishlistPage;
    private final CheckoutPage checkoutPage;
    private final HomePage homePage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public WishlistSteps(TestContext context) {

        wishlistPage = context.wishlistPage;
        checkoutPage = context.checkoutPage;
        homePage = context.homePage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    //Wishlist PLP
    @Given("user clicks on the Wishlist icon")
    public void user_clicks_on_the_wishlist_icon() {

        wishlistPage.clickOnWishlistIcon();
    }
    @Then("user validates the wishlisted products list")
    public void user_validates_the_wishlisted_products_list() {

        wishlistPage.fetchProductList(wishlistPage.wishlistProducts);
    }
    @When("user clicks on the Women category")
    public void user_clicks_on_the_women_category() {

        homePage.moveToCategory(homePage.women);
    }
    @When("user selects a sub-category under Women")
    public void user_selects_a_sub_category_under_women() throws InterruptedException {

        homePage.clickOnSubCategory(homePage.topsAndTshirt);
    }
    @When("user adds a product to the wishlist from the product listing page")
    public void user_adds_a_product_to_the_wishlist_from_the_product_listing_page() {

        try {
            user_removes_the_product_from_the_wishlist_on_the_product_listing_page();
        } catch(RuntimeException e) {
            wishlistPage.addProductsToWishlist(3);
        }
    }
    @Then("user removes the product from the wishlist on the product listing page")
    public void user_removes_the_product_from_the_wishlist_on_the_product_listing_page() {

        wishlistPage.removeProductsFromWishlist(3);
    }

    //Wishlist PDP
    @Given("user clicks on the Kids category")
    public void user_clicks_on_the_kids_category() {

        homePage.moveToCategory(homePage.kids);
    }
    @When("user selects a sub-category under Kids")
    public void user_selects_a_sub_category_under_kids() throws InterruptedException {

        homePage.clickOnSubCategory(homePage.blazerKids);
    }
    @When("user selects a product from the product listing page")
    public void user_selects_a_product_from_the_product_listing_page() {

        boolean result = checkoutPage.clickProductByIndex(checkoutPage.productList, 2);
        if (!result) {
            Assert.fail("Could not click product at index 2 from PLP");
        } else {
            scenario.log("Product clicked successfully at index 2");
        }
    }
    @When("user adds a product to the wishlist from the product details page")
    public void user_adds_a_product_to_the_wishlist_from_the_product_details_page() {

        wishlistPage.clickOnWishlistPDP();
    }
    @Then("user validates that the product is added to the wishlist successfully")
    public void user_validates_that_the_product_is_added_to_the_wishlist_successfully() {

        wishlistPage.validateErrorMessageByPartialText("Added to your Wishlist", "Added to your Wishlist");
    }
    @Then("user removes the product from the wishlist on the product details page")
    public void user_removes_the_product_from_the_wishlist_on_the_product_details_page() {

        wishlistPage.clickOnWishlistPDP();
    }
    @Then("user validates that the product is removed from the wishlist successfully")
    public void user_validates_that_the_product_is_removed_from_the_wishlist_successfully() {

        wishlistPage.validateErrorMessageByPartialText("Removed from your Wishlist", "Removed from your Wishlist");
    }


    // Wishlist - Adding to Cart
    @When("user adds a product to the bag from the wishlist")
    public void user_adds_a_product_to_the_bag_from_the_wishlist() {

        boolean result = checkoutPage.clickProductByIndex(checkoutPage.productList, 2);
        if (!result) {
            Assert.fail("Could not click product at index 2 from PLP");
        } else {
            scenario.log("Product clicked successfully at index 2");
        }
    }

    //Wishlist - You Might also like section
    @When("user adds a product to the wishlist from the You Might Also Like section")
    public void user_adds_a_product_to_the_wishlist_from_the_you_might_also_like_section() {

        wishlistPage.scrollToElement(wishlistPage.youMight);
        wishlistPage.addProductsToWishlist(1);
    }
    @Then("user removes the product from the wishlist in the You Might Also Like section")
    public void user_removes_the_product_from_the_wishlist_in_the__you_might_also_like_section() {

        wishlistPage.removeProductsFromWishlist(3);
    }

    //Add to bag - You Might also like section
    @Then("user adds a product to the bag from the You Might Also Like section")
    public void user_adds_a_product_to_the_bag_from_the_you_might_also_like_section() throws InterruptedException {

        Thread.sleep(1000);
        wishlistPage.scrollToElement(wishlistPage.youMight);
        wishlistPage.scrollDown();
        wishlistPage.clickAddToBagByIndex(1);
    }
    @Then("user selects the size")
    public void user_selects_the_size() {

        wishlistPage.clickByIndexIfAnyAvailable();
    }

}
