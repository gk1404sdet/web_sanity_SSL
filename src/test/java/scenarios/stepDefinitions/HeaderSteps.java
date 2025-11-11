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
    @When("user verifies navigation for each category")
    public void user_verifies_navigation_for_each_category() {

        headerPage.navigateThroughTopCategoriesWithUrlMapping();
    }

    //Search
    @Given("user clicks on the search bar")
    public void user_clicks_on_the_search_bar() {

        headerPage.clicksOnTheSearEditBox();
    }
    @When("user validates Trending Search suggestions")
    public void user_validates_trending_search_suggestions() {

        List<String> trendingSearch = headerPage.extractProductNames(headerPage.trendingSearch, "Trending Search");
    }
    @When("user validates that from Trending Search suggestions landing the relevant products")
    public void user_validates_that_from_trending_search_suggestions_landing_the_relevant_products() {

        headerPage.clickProductByIndex(headerPage.trendingSearch, 7, "Trending Search navigation");
    }
    @Then("user validates Popular Brands suggestions")
    public void user_validates_popular_brands_suggestions() {

        List<String> popularBrands = headerPage.extractProductNames(headerPage.popularBrands, "Popular Brands");
    }
    @Then("user validates that from Popular Brands suggestions landing the relevant products")
    public void user_validates_that_from_popular_brands_suggestions_landing_the_relevant_products() {

        headerPage.clickProductByIndex(headerPage.popularBrands, 3, "Popular Brands navigation");
    }
    @Then("user searches with dynamic keywords")
    public void user_searches_with_dynamic_keywords() {

        String key = configLoader.getProperty("search.keyword");
        headerPage.entersTheDynamicSearchKeyword(key);
    }

    //Store And Events
    @When("user clicks on the Store and Events icon")
    public void user_clicks_on_the_store_and_events_icon() {

        headerPage.clicksOnTheStoreIcon();
    }
    @When("user clicks on the dropdown option to select city")
    public void user_clicks_on_the_dropdown_option_to_select_city() {

        headerPage.clicksOnTheDropDown();
    }
    @When("user selects the city in the stores list")
    public void user_selects_the_city_in_the_stores_list() {

        String city = configLoader.getProperty("appointment.cityIndex");
        headerPage.selectCity(city);
    }

    //Fashion Stylist
    @When("user clicks on the Fashion Stylist")
    public void user_clicks_the_fashion_stylist() {

        headerPage.clicksOnTheFashionStylist();
    }
    @When("user enters the query")
    public void user_enters_the_query() {

        String key = configLoader.getProperty("search.fashionPrompt");
        headerPage.entersTheQuery(key);
    }
    @Then("user validates the suggested product list")
    public void user_validates_the_suggested_product_list() {

        headerPage.printSuggestedProductDetails();
    }

    @When("user switches back to the main window")
    public void user_switches_back_to_the_main_windows() {

        headerPage.switchToDefaultContent();
    }
}
















