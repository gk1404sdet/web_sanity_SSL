Feature: Verify the Home Page Functionality

  Background:
  @smoke @sanity @TC002 @book
  Scenario Outline: Verify the sign-in functionality via phone number for existing user
    Given user launches the application
    When user taps on the Login button
    And user enters the mobile number "<mob no>"
    And user clicks on the Proceed button
    Then user enters the OTP "<otp>"
    And user clicks on the Verify OTP button
    Then system should display the appropriate login status

    Examples:
      | mob no     | otp    |
      | 9136734340 | 123456 |

  @sanity @TC011 @TC012
  Scenario: Verify the Sort Functionality

    Given user clicks on the Women category
    When user selects a sub-category under Women
    And user clicks on the Sort By option
    And user applies sort by as Price: Low to High
    And user clicks on the Men category
    When user selects a sub-category under Men
    And user clicks on the Sort By option
    And user applies sort by as Discount
    Then user clicks on the Women category
    And user selects a sub-category under Women
    And user clicks on the Sort By option
    And user applies sort by as New
    And user clicks on the Bargains category
    Then user selects a sub-category under Beauty
    And user clicks on the Sort By option
    And user applies sort by as Price: High to Low

  @sanity @TC011 @TC012
  Scenario: Verify the Filter Functionality

    Given user clicks on the Women category
    When user selects a sub-category under Women Sarees
    And user clicks on the Sort By option
    And user applies sort by as Price: Low to High
    And user applies the Brand filter
    And user applies the Size filter
    And user applies the Price filter
    Then user applies the Color filter

  @smoke @sanity @TC013
  Scenario: Verify the components of PDP

    Given user clicks on the Men category
    When user selects a sub-category under Men
    And user selects a product from the PLP
    Then user is navigated to the Product Detail Page
    And user validates that all Product Detail section components are displayed
      | Product Details   |
      | About The Brand   |
      | Ratings & Reviews |
      | Check Delivery    |
    And user validates the product details

  @smoke @sanity @TC013 @TC020
  Scenario: Verify functionality of invalid postal code check on the PDP

    Given user checks the postal code on PDP
    When user clicks on the postal code check box
    And user enters an invalid postal code
    Then system should display the appropriate error message

  @smoke @sanity @TC013 @TC065
  Scenario: Verify functionality of valid postal code check on the PDP

    Given user clicks on the postal code check box
    When user enters the postal code
    Then user clicks on the check option to validate the postal code
    And user navigates back to the Home Page

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out