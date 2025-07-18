package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.HomePage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.util.Arrays;

public class HomeSteps {

    private final HomePage homePage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public HomeSteps(TestContext context) {

        homePage = context.homePage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    // Sort by
    @Given("user clicks on the bargains category")
    public void user_clicks_on_the_bargains_category() {
        homePage.moveToElement(homePage.bargains);
    }
    @When("user selects a sub-category under bargains")
    public void user_selects_a_sub_category_under_bargains() {
        homePage.clickOnSubCategory(homePage.dressWomen);
    }
    @When("user clicks on the sort by")
    public void user_clicks_on_the_sort_by() {
        homePage.clickOnSortBy();
    }
    @When("user applying the sort by as price low to high")
    public void user_applying_the_sort_by_as_price_low_to_high() {
        homePage.applyFilter(homePage.lowToHigh);
    }
    @When("user selects a sub-category under men")
    public void user_selects_a_sub_category_under_men() {
        homePage.clickOnSubCategory(homePage.shirt);
    }
    @When("user applying the sort by as discount")
    public void user_applying_the_sort_by_as_discount() {
        homePage.applyFilter(homePage.discount);
    }
    @Then("user selects a sub-category under home offer")
    public void user_selects_a_sub_category_under_home_offer() {
        homePage.clickOnSubCategory(homePage.bed);
    }
    @Then("user applying the sort by as new")
    public void user_applying_the_sort_by_as_new() {
        homePage.applyFilter(homePage.newSort);
    }
    @Then("user selects a sub-category under beauty")
    public void user_selects_a_sub_category_under_beauty() {
        homePage.clickOnSubCategory(homePage.womenFragrance);
    }
    @Then("user applying the sort by as price high to low")
    public void user_applying_the_sort_by_as_price_high_to_low() {
        homePage.applyFilter(homePage.highToLow);
    }

    // Filter
    @When("user selects a sub-category under Women sarees")
    public void user_selects_a_sub_category_under_women_sarees() {
        homePage.clickOnSubCategory(homePage.sarees);
    }
    @When("user applying the Brands filter")
    public void user_applying_the_brands_filter() {
        homePage.printAndClickFilterOptions("Brands", Arrays.asList(2,1));
    }
    @When("user applying the Size filter")
    public void user_applying_the_size_filter() {
        homePage.printAndClickFilterOptions("Size", Arrays.asList(1));
    }
    @When("user applying the Price filter")
    public void user_applying_the_price_filter() {
        homePage.printAndClickFilterOptions("Price", Arrays.asList(1,3));
    }
    @Then("user applying the Color filter")
    public void user_applying_the_color_filter() {
        homePage.printAndClickFilterOptions("Color", Arrays.asList(1,2));
    }

    // PDP components
    @Then("user validates that product details")
    public void user_validates_that_product_details() {
        homePage.printProductDetails();
    }


}
