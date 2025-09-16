Feature: Verify the Wishlist Functionality

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

@smoke @sanity @TC015
  Scenario: Verify adding/removing products from PLP

    Given user clicks on the Wishlist icon
    When user validates the wish listed products list
    And user clicks on the Women category
    And user selects a sub-category under Women
    When user adds a product to the wishlist from the product listing page
    And user clicks on the Wishlist icon
    When user validates the wish listed products list
    And user clicks on the Women category
    And user selects a sub-category under Women
    And user removes the product from the wishlist from the product listing page
    And user clicks on the Wishlist icon
    When user validates the wish listed products list

@smoke @sanity @TC016
  Scenario: Verify adding/removing products from PDP

    Given user clicks on the kids category
    When user selects a sub-category under kids
    And user selects the product from the product listing page
    And user is navigated to the product detail page
    And user add a product to the wishlist from the product details page
    Then user validates that product add wishlist successfully
    And user removes the product from the wishlist from the product details page
    Then user validates that product removed wishlist successfully

@sanity @TC022
  Scenario: Verify adding/removing products from wishlist to bag/cart

    Given user back to Home Page
    When user clicks on the Wishlist icon
    When user validates the wish listed products list
    And user adds a product to bag from wishlist
    And user is navigated to the product detail page
    And user validating the out of stock product
    Then user selects a size if available
    And user clicks on Add to Bag and View Bag

@sanity @TC017
  Scenario: Verify adding/removing products from Wishlist page (You Might also Like)

    Given user back to Home Page
    And user clicks on the Wishlist icon
    When user add a product to wishlist from you might also like section
    And user validates that product add wishlist successfully
    Then user removes the product to wishlist from you might also like section
    And user validates that product removed wishlist successfully

@sanity @TC024
  Scenario: Verify adding/removing products from Wishlist page to cart/bag (You Might also Like)

    When user add a product to add to bag from you might also like section
    And user selects the size

@smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out
