Feature: Verify the Home Page Functionality

  Background:
  @smoke @sanity @TC002
  Scenario Outline: Verify the Sign-in functionality via phone number for existing user

    Given user launches the application
    When user taps on the Login button
    And user enters mobile number "<mob no>"
    And user clicks on the Proceed button
    Then user enters OTP "<otp>"
    And user clicks on the Verify OTP button
    Then system should display the appropriate login status

    Examples:
      | mob no | otp |
      | 9136734340 | 123456 |

@sanity @TC011 @TC012
  Scenario: TES3848 - Verify the Sort Functionality

    Given user clicks on the Women category
    When user selects a sub-category under Women
    And user clicks on the sort by
    And user applying the sort by as price low to high
    And user clicks on the Men category
    When user selects a sub-category under Men
    And user clicks on the sort by
    And user applying the sort by as discount
    Then user clicks on the Women category
    Then user selects a sub-category under Women
    And user clicks on the sort by
    And user applying the sort by as new
    And user clicks on the bargains category
    Then user selects a sub-category under beauty
    And user clicks on the sort by
    And user applying the sort by as price high to low

@sanity @TC011 @TC012
  Scenario: TES3848 - Verify the Filter Functionality

    Given user clicks on the Women category
    When user selects a sub-category under Women sarees
    And user clicks on the sort by
    And user applying the sort by as price low to high
    And user applying the Brands filter
    And user applying the Size filter
    And user applying the Price filter
    Then user applying the Color filter

@smoke @sanity @TC013
  Scenario: TES349 - Verify the components of PDP

    Given user clicks on the Men category
    When user selects a sub-category under Men
    And user selects a product from the PLP
    Then user is navigated to the product detail page
    And user validates all product detail section components are displayed
      | Product Details        |
      | About The Brand        |
      | RATINGS & REVIEWS      |
      | Check Delivery         |
    And user validates that product details

@smoke @sanity @TC013 @TC020
  Scenario: Verify functionality of Invalid Postal code check on the PDP

    Given user check the postal code from pdp
    When user clicks the postal code check box
    When user enters the invalid postal code
    And system should display the appropriate error message

@smoke @sanity @TC013 @TC065
  Scenario: Verify functionality of Postal code check on the PDP

    Given user clicks the postal code check box
    When user enters the postal code
    Then user clicks the check option to validate the postal code
    And user back to Home Page

@smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out