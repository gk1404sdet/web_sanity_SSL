Feature: Verify Successful and Failed Payment Modes

  @smoke @sanity @TC002
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

  @smoke @sanity @TC014 @TC028 @TC029 @TC030 @TC032 @TC033 @TC042
  Scenario: Verify a successful payment
    Given user clicks on the Men category
    When user selects a sub-category under Men
    And user selects a product from the PLP
    Then user is navigated to the Product Detail Page
    And user validates the out of stock product
    When user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on View Bag
    Then user validates the price details in the bag
    When user clicks on Place Order
    And user selects the delivery address
    Then user validates the price details in the bag
    When user clicks on the Continue button
    And user verifies the available payment modes
    Then user validates the price details in the bag
    And user checks if Cash on Delivery is available
    When user clicks on Place Order

  @smoke @sanity @TC038 @TC039
  Scenario: Verify navigation to the successful order summary page
    Then user validates the successful order summary
    And user validates the delivery mode and address
    Then user validates the final price details

  @smoke @sanity @TC043 @TC044
  Scenario: Verify functionality of review shopping experience
    When user verifies the review shopping experience
    And user gives a rating for shopping
    And user selects the What can be improved option
    And user submits the review
    Then user clicks on the Continue Shopping button

  @sanity @TC014 @TC028 @TC029 @TC030 @TC032 @TC033 @TC037 @TC042
  Scenario: Verify a failed payment
    Given user is on the Home Page
    And user clicks on the Men category
    When user selects a sub-category under Men
    And user selects a product from the PLP
    Then user is navigated to the Product Detail Page
    And user validates the out of stock product
    And user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on View Bag
    Then user validates the price details in the bag
    When user clicks on Place Order
    And user selects the delivery address
    Then user validates the price details in the bag
    When user clicks on the Continue button
    And user verifies the available payment modes
    Then user validates the price details in the bag
    And user selects the Wallet option
    Then user navigates back to the Home Page

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality
    Given user is on the Home Page
    When user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out