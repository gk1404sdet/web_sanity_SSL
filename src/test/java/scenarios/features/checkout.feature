Feature: Verify Successful and Failed Payment mode

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

@smoke @sanity @TC014 @TC028 @TC029 @TC030 @TC032 @TC033 @TC042
  Scenario: TES3869 - Verify a successful payment

    Given user clicks on the Men category
    When user selects a sub-category under Men
    And user selects a product from the PLP
    Then user is navigated to the product detail page
    And user validating the out of stock product
    When user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on the View Bag
    Then user validates the price details in the bag
    When user clicks on Place Order
    When user selects the delivery address
    Then user validates the price details in the bag
    When user clicks the Continue button
    And user verifies the available payment modes
    Then user validates the price details in the bag
    Then user checks if Cash on Delivery is available
    When user clicks on Place Order

@smoke @sanity @TC038 @TC039
  Scenario: TES3874 - Verify the navigation to successful order summary page

    Then user validating successful order summary
    And user validating the Deliver Mode and Address
    Then user validates the final price details

@smoke @sanity @TC043 @TC044
  Scenario: TES3874 - Verify functionality of Review Shopping Experience

    When user verifying the review shopping experience
    And user giving the rate for shopping
    And user selecting the what can be improved option
    And user submit the review
    Then user clicks the Continue Shopping Button

@sanity @TC014 @TC028 @TC029 @TC030 @TC032 @TC033 @TC037 @TC042
  Scenario: TES3873 - Verify a Failed payment

    Given user back to Home Page
    Given user is on the Home Page
    Given user clicks on the Women category
    When user selects a sub-category under Women
    And user selects a product from the PLP
    Then user is navigated to the product detail page
    Then user validating the out of stock product
    And user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on the View Bag
    Then user validates the price details in the bag
    When user clicks on Place Order
    When user selects the delivery address
    Then user validates the price details in the bag
    When user clicks the Continue button
    And user verifies the available payment modes
    Then user validates the price details in the bag
    Then user selecting the Wallet Option
    Then user back to Home Page

@smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user back to Home Page
    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out