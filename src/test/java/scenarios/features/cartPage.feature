Feature: Verify the Cart Page Functionality

  Background:
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

  @sanity @TC031
  Scenario: Verify CRUD on the Address page

    Given user clicks on the Men category
    When user selects a sub-category under Men
    And user selects a product from the PLP
    And user is navigated to the Product Detail Page
    And user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on View Bag
    And user clicks on Place Order
    Then user updates an existing address
    And user updates their name
    And user updates number
    And user updates pin code
    And user updates address
    And user clicks on Update Changes
    Then user clicks on the add new address
    And user enters new name
    And user enters new number
    And user enters new pin code
    And user enters new address
    And user selects address type as Home
    And user clicks on Add Address
    And user validates that new address is added successfully

  @smoke @sanity @TC018
  Scenario: Verify the components of the Bag

    When user navigates back to the Home Page
    And user is on the Home Page
    And user clicks on the Cart icon
    Then system should display the following components on the cart page
      | Offers for You         |
      | Check Delivery         |
      | Gift Wrap              |
      | Have a Coupon Code?    |
      | Price Details          |

  @smoke @sanity @TC020
  Scenario: Verify functionality of invalid postal code check on the cart page

    Given user clicks on the postal code check box
    When user enters an invalid postal code "08624"
    Then user clicks on the check option to validate the postal code
    And system should display the appropriate error message

  @smoke @sanity @TC020
  Scenario: Verify functionality of valid postal code check on the cart page

    Given user clicks on the postal code check box
    When user enters the postal code "560076"
    Then user clicks on the check option to validate the postal code

  @smoke @sanity @TC026
  Scenario: Verify increase and decrease of product quantity

    When user increases the quantity from the cart
    Then user decreases the quantity from the cart

  @sanity @TC027
  Scenario: Verify adding/removing coupons and Gift Wrap message

    When user clicks on the Gift Wrap option
    And user enters the receiver name
    And user enters the gift message "Happy Birthday"
    And user enters the sender name
    Then user clicks on save gift message
    And user validates that Gift Wrap details added successfully
    And user clicks on the Gift Wrap option
    And user validates that Gift Wrap details removed successfully

  @sanity @TC025
  Scenario: Verify functionality of size change on the cart page

    Given user clicks on the size change option
    When user validates that available size is displayed

  @smoke @sanity @TC019
  Scenario: Verify adding/removing product to bag from PDP and move to Wishlist

    When user clicks on the X-mark icon
    And user moves the product to wishlist from bag
    When user clicks on the X-mark icon
    Then user removes the product from bag
    And user validates that product is removed from bag

# @sanity @TC024
# Scenario: TES3860 - Verify adding/removing product to bag from "You May Also Like" on the Bag page
#
#   Given user adds a product to the bag from "You May Also Like"
#   When user clicks on Add to Bag
#   And user selects the size
#   Then user validates that product is added to cart successfully

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality
    Given user navigates back to the Home Page
    And user is on the Home Page
    When user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out