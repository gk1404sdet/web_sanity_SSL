package scenarios.stepDefinitions;

import context.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.CartPage;
import pages.HomePage;
import utilities.ConfigLoader;
import utilities.CredsLoader;

import java.util.Arrays;

public class HomeSteps {

    private final HomePage homePage;
    private final CartPage cartPage;
    CredsLoader credsLoader;
    ConfigLoader configLoader;
    Scenario scenario;

    public HomeSteps(TestContext context) {

        homePage = context.homePage;
        cartPage = context.cartPage;
        this.credsLoader = context.credsLoader;
        this.configLoader = context.configLoader;
        this.scenario = context.scenario;
    }

    // Sort by
    @Given("user clicks on the Bargains category")
    public void user_clicks_on_the_bargains_category() {

        homePage.moveToCategory(homePage.bargains);
    }
    @When("user clicks on the Sort By option")
    public void user_clicks_on_the_sort_by_option() {

        homePage.clickOnSortBy();
    }
    @When("user applies sort by as Price: Low to High")
    public void user_applies_sort_by_as_price_low_to_high() {

        homePage.applyFilter(homePage.lowToHigh);
    }
    @When("user applies sort by as Discount")
    public void user_applies_sort_by_as_discount() {

        homePage.applyFilter(homePage.discount);
    }
    @Then("user applies sort by as New")
    public void user_applies_sort_by_as_new() {

        homePage.applyFilter(homePage.newSort);
    }
    @Then("user selects a sub-category under Beauty")
    public void user_selects_a_sub_category_under_beauty() throws InterruptedException {

        homePage.clickOnSubCategory(homePage.womenFragrance);
    }
    @Then("user applies sort by as Price: High to Low")
    public void user_applies_sort_by_as_price_high_to_low() {

        homePage.applyFilter(homePage.highToLow);
    }

    // Filter
    @When("user selects a sub-category under Women Sarees")
    public void user_selects_a_sub_category_under_women_sarees() throws InterruptedException {

        homePage.clickOnSubCategory(homePage.sarees);
    }
    @When("user applies the Brand filter")
    public void user_applies_the_brand_filter() {

        homePage.printAndClickFilterOptions("Brands", Arrays.asList(2,1));
    }
    @When("user applies the Size filter")
    public void user_applies_the_size_filter() {

        homePage.printAndClickFilterOptions("Size", Arrays.asList(1));
    }
    @When("user applies the Price filter")
    public void user_applies_the_price_filter() {

        homePage.printAndClickFilterOptions("Price", Arrays.asList(1,3));
    }
    @Then("user applies the Color filter")
    public void user_applies_the_color_filter() {

        homePage.printAndClickFilterOptions("Color", Arrays.asList(1,2));
    }

    // PDP components
    @Then("user validates the product details")
    public void user_validates_that_product_details() {

        homePage.printProductDetails();
    }

    //PDP Pin code Check
    @Given("user checks the postal code on PDP")
    public void user_checks_the_postal_code_on_pdp() throws InterruptedException {

        Thread.sleep(2000);
        homePage.scrollToElement(cartPage.pincodeBox);
    }

}
