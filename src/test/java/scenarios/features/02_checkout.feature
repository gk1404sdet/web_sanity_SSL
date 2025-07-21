
Feature: Verify Successful and Failed Payment mode
  @checkout
  Scenario: TES23 - Verify the Sign-in functionality via phone number for existing user

    Given user launches the application
    When user taps on the Login button
    And user enters mobile number "9136734340"
    And user clicks on the Proceed button
    Then user enters OTP "123456"
    And user clicks on the Verify OTP button
    Then system should display the appropriate login status

@successfulPayment
  Scenario: TES60 - Verify a successful payment

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

@orderSummary
  Scenario: TES61 - Verify the navigation to successful order summary page

    Given user validating successful order summary
    When user validating the Deliver Mode and Address
    Then user validates the final price details

@reviewShopping
  Scenario: TES65 - Verify functionality of Review Shopping Experience

    When user verifying the review shopping experience
    And user giving the rate for shopping
    And user selecting the what can be improved option
    And user submit the review
    Then user clicks the Continue Shopping Button

@failedPayment @checkout
  Scenario: TES59 - Verify a Failed payment

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
    Then system should display the Something Went Wrong Message
    Then user back to Home Page