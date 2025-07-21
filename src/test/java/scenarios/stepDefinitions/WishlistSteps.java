package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
    @Then("user validates the wish listed products list")
    public void user_validates_the_wish_listed_products_list() {
        wishlistPage.fetchProductList(wishlistPage.wishlistProducts);
    }
    @When("user clicks on the Women category")
    public void user_clicks_on_the_women_category() {
        wishlistPage.moveToElement(homePage.women);
    }
    @When("user selects a sub-category under Women")
    public void user_selects_a_sub_category_under_women() {
        homePage.clickOnSubCategory(homePage.topsAndTshirt);
    }
    @When("user adds a product to the wishlist from the product listing page")
    public void user_adds_a_product_to_the_wishlist_from_the_product_listing_page() throws InterruptedException {
        wishlistPage.addProductsToWishlist(3);
    }
    @Then("user removes the product from the wishlist from the product listing page")
    public void user_removes_the_product_from_the_wishlist_from_the_product_listing_page() throws InterruptedException {
        wishlistPage.removeProductsFromWishlist(3);
    }

    //Wishlist PDP
    @Given("user clicks on the kids category")
    public void user_clicks_on_the_kids_category() {
        wishlistPage.moveToElement(homePage.kids);
    }
    @When("user selects a sub-category under kids")
    public void user_selects_a_sub_category_under_kids() {
        homePage.clickOnSubCategory(homePage.blazerKids);
    }
    @When("user selects the product from the product listing page")
    public void user_selects_the_product_from_the_product_listing_page() {
        checkoutPage.clickProductByIndex(checkoutPage.productList, 3);
    }
    @When("user add a product to the wishlist from the product details page")
    public void user_add_a_product_to_the_wishlist_from_the_product_details_page() {
        wishlistPage.clickOnWishlistPDP();
    }
    @Then("user validates that product add wishlist successfully")
    public void user_validates_that_product_add_wishlist_successfully() {
        wishlistPage.validateErrorMessageByPartialText("Added to your Wishlist", "Added to your Wishlist");
    }
    @Then("user removes the product from the wishlist from the product details page")
    public void user_removes_the_product_from_the_wishlist_from_the_product_details_page() {
        wishlistPage.clickOnWishlistPDP();
    }
    @Then("user validates that product removed wishlist successfully")
    public void user_validates_that_product_removed_wishlist_successfully() {
        wishlistPage.validateErrorMessageByPartialText("Removed from your Wishlist", "Removed from your Wishlist");
    }

    // Wishlist - Adding to Cart
    @When("user adda a product to bag from wishlist")
    public void user_adda_a_product_to_bag_from_wishlist() {
        checkoutPage.clickProductByIndex(wishlistPage.wishlistProducts, 3);
    }

    //Wishlist - You Might also like section
    @When("user add a product to wishlist from you might also like section")
    public void user_add_a_product_to_wishlist_from_you_might_also_like_section() {
        wishlistPage.scrollToElement(wishlistPage.youMight);
        wishlistPage.addProductsToWishlist(1);
    }
    @Then("user removes the product to wishlist from you might also like section")
    public void user_removes_the_product_to_wishlist_from_you_might_also_like_section() {
        wishlistPage.removeProductsFromWishlist(2);
    }

    //Add to bag - You Might also like section
    @When("user add a product to add to bag from you might also like section")
    public void user_add_a_product_to_add_to_bag_from_you_might_also_like_section() {
        wishlistPage.clickAddToBagByIndex(1);
    }
    @When("user selects the size")
    public void user_selects_the_size() {
        wishlistPage.clickByIndexIfAnyAvailable();
    }

}
