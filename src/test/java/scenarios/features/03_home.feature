@home
Feature: Verify the Home Page Functionality

@sort
  Scenario: TES33 - Verify the Sort Functionality

  Given user back to Home Page
    Given user clicks on the bargains category
    When user selects a sub-category under bargains
    And user clicks on the sort by
    And user applying the sort by as price low to high
    And user clicks on the bargains category
    When user selects a sub-category under men
    And user clicks on the sort by
    And user applying the sort by as discount
    Then user clicks on the bargains category
    Then user selects a sub-category under home offer
    And user clicks on the sort by
    And user applying the sort by as new
    And user clicks on the bargains category
    Then user selects a sub-category under beauty
    And user clicks on the sort by
    And user applying the sort by as price high to low

@filter
  Scenario: TES33 - Verify the Filter Functionality

    Given user clicks on the Women category
    When user selects a sub-category under Women sarees
    And user clicks on the sort by
    And user applying the sort by as price low to high
    And user applying the Brands filter
    And user applying the Size filter
    And user applying the Price filter
    Then user applying the Color filter

@pdpComponents
  Scenario: TES34 - Verify the components of PDP

    Given user clicks on the Women category
    When user selects a sub-category under Women sarees
    And user selects a product from the PLP
    Then user is navigated to the product detail page
    And user validates that product details

@invalidPincodePDP
  Scenario: Verify functionality of Pincode check on the PDP

    Given user clicks the pincode check box
    When user enters the invalid pincode
    Then user clicks the check option to validate the pincode
    And system should display the appropriate error message

@validPincodePDP
  Scenario: Verify functionality of Pincode check on the PDP

    Given user clicks the pincode check box
    When user enters the pincode
    Then user clicks the check option to validate the pincode
    And user back to Home Page


