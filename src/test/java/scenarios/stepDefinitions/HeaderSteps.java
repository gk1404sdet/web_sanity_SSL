package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HeaderPage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.util.List;


public class HeaderSteps {

    private final HeaderPage headerPage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public HeaderSteps(TestContext context) {

        headerPage = context.headerPage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    //Top Categories
    @Given("user is on the Home Page")
    public void user_is_on_the_home_page() {
        scenario.log("User is On the Home Page");
    }

    @When("system should display the following components in the Top Categories section")
    public void system_should_display_the_following_components_in_the_top_categories_section(DataTable dataTable) {
        List<String> expectedComponents = dataTable.asList();
        List<String> actualComponents = headerPage.getAllComponentList();
        for (String expected : expectedComponents) {
            Assert.assertTrue(actualComponents.contains(expected), "Missing Component: " + expected);
            scenario.log("Verified Top Categories Components: " + expected);
        }
    }

    @When("verifying the each category navigation")
    public void verifying_the_each_category_navigation() {
        headerPage.navigateThroughTopCategoriesWithUrlMapping();
    }

    //Fashion Stylist
    @When("user clicks the Fashion Stylist")
    public void user_clicks_the_fashion_stylist() {
        headerPage.clicksOnTheFashionStylist();
    }

    @When("user enters the query")
    public void user_enters_the_query() {
        headerPage.entersTheQuery("Suggest me a party dress");
    }

    @Then("user validating the suggested product list")
    public void user_validating_the_suggested_product_list() {
        headerPage.printSuggestedProductDetails();
    }

    //Search
    @Given("user clicks on the search bar")
    public void user_clicks_on_the_search_bar() {
        headerPage.clicksOnTheSearEditBox();
    }

    @When("user validates that Trending Search suggestion")
    public void user_validates_that_trending_search_suggestion() {
        List<String> trendingSearch = headerPage.extractProductNames(headerPage.trendingSearch, "Trending Search");
    }

    @When("user validates that from Trending Search suggestion landing the relevant products")
    public void user_validates_that_from_trending_search_suggestion_landing_the_relevant_products() {
        headerPage.clickProductByIndex(headerPage.trendingSearch, 7, "Trending Search navigation");
    }

    @When("user validates that Trending Products suggestion")
    public void user_validates_that_trending_products_suggestion() {
        List<String> trendingProducts = headerPage.extractProductNames(headerPage.trendingProduct, "Trending Products");
    }

    @When("user validates that from Trending Products suggestion landing the relevant products")
    public void user_validates_that_from_trending_products_suggestion_landing_the_relevant_products() {
        headerPage.clickProductByIndex(headerPage.trendingProduct, 1, "Trending Products navigation");
    }

    @Then("user validates that Popular Brands")
    public void user_validates_that_popular_brands() {
        List<String> popularBrands = headerPage.extractProductNames(headerPage.popularBrands, "Popular Brands");
    }

    @Then("user validates that from Popular Brands suggestion landing the relevant products")
    public void user_validates_that_from_popular_brands_suggestion_landing_the_relevant_products() {
        headerPage.clickProductByIndex(headerPage.popularBrands, 2, "Popular Brands navigation");
    }

    @Then("user search with dynamic keywords")
    public void user_search_with_dynamic_keywords() {
        headerPage.entersTheDynamicSearchKeyword("Kurta for Women");
    }

    //Store And Events
    @When("user clicks on Store and Events icon")
    public void user_clicks_on_store_and_events_icon() {
        headerPage.clicksOnTheStoreIcon();
    }

    @When("user clicks on the drop down option for select city")
    public void user_clicks_on_the_drop_down_option_for_select_city() {
        headerPage.clicksOnTheDropDown();
    }

    @When("user selects the city in the stores")
    public void user_selects_the_city_in_the_stores() {
        headerPage.selectCity("8");
    }

    @When("user back to normal windows")
    public void user_back_to_normal_windows() {
        headerPage.switchToDefaultContent();
    }


}
















