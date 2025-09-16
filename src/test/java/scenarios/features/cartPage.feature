
Feature: Verify the Cart Page Functionality

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

@sanity @TC031
  Scenario: Verify CRUD on the Address page

    Given user clicks on the Men category
    When user selects a sub-category under Men
    And user selects the product from the product listing page
    And user is navigated to the product detail page
    And user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on the View Bag
    And user clicks on Place Order
    Then user updates an existing address
    And user updates their name
    And user updates their number
    And user updates their pin code
    And user updates their address
    And user clicks on the update changes
    Then user clicks on the add new address
    And user enters the new name
    And user enters the new number
    And user enters the new pin code
    And user enters the new address
    And user selects a address type as home
    And user clicks on the add address
    And user validate that new address added successfully

@smoke @sanity @TC018
  Scenario: Verify the components of Bag

    When user back to Home Page
    When user clicks on the Cart icon
    When system should display the following components in the cart page
      | Offers for you         |
      | Check Delivery         |
      | GIFT WRAP              |
      | HAVE A COUPON CODE?    |
      | Price Details          |

@smoke @sanity @TC020
  Scenario: Verify functionality of Invalid Pincode check on the cart page

    Given user clicks the pincode check box
    When user enters the invalid pincode
    Then user clicks the check option to validate the pincode
    And system should display the appropriate error status

@smoke @sanity @TC020
  Scenario: Verify functionality of Valid Pincode check on the cart page

    Given user clicks the pincode check box
    When user enters the pincode
    Then user clicks the check option to validate the pincode

@smoke @sanity @TC026
  Scenario: Verify increase and decrease of the product quantity

    When user increase the quantity from the cart
    Then user decrease the quantity from the cart

@sanity @TC027
  Scenario: Verify adding/removing coupons and Gift Wrap message

    When user clicks gift wrap option
    And user enters the receiver's name
    And user enters the gift message
    And user enters the sender's name
    Then user clicks on the save gift message
    And user validate that gift wrap details added successfully
    And user clicks gift wrap option
    And user validate that gift wrap details removed successfully

@sanity @TC025
  Scenario: Verify functionality of size changing on the cart page

    Given user clicks the size changing option
    When user validate that available size

@smoke @sanity @TC019
  Scenario: Verify adding/removing product to bag from PDP and Move to Wishlist

    When user clicks on the xmark icon
    And user is moving the product to wishlist from bag
    And user validates that product moved to wishlist from bag
    When user clicks on the xmark icon
    Then user is removing the product from bag
    And user validates that product removed from bag
    And user is on the Home Page

#@sanity @TC024
#  Scenario: Verify adding/removing product to bag from You May Also Like on the Bag page
#
#    Given user add a product to the bag from you may also like
#    When user clicks on the add to bag
#    And user selects the size
#    Then user validates that product add to cart successfully

@smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user back to Home Page
    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out