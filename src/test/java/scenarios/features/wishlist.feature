Feature: Verify the Wishlist Functionality

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

  @smoke @sanity @TC015
  Scenario: Verify adding/removing products from PLP

    Given user clicks on the Wishlist icon
    When user validates the wishlisted products list
    And user clicks on the Women category
    And user selects a sub-category under Women
    When user adds a product to the wishlist from the product listing page
    And user clicks on the Wishlist icon
    When user validates the wishlisted products list
    And user clicks on the Women category
    And user selects a sub-category under Women
    And user removes the product from the wishlist on the product listing page
    And user clicks on the Wishlist icon
    When user validates the wishlisted products list

  @smoke @sanity @TC016
  Scenario: Verify adding/removing products from PDP

    Given user clicks on the Kids category
    When user selects a sub-category under Kids
    And user selects a product from the product listing page
    And user is navigated to the Product Detail Page
    And user adds a product to the wishlist from the product details page
    Then user validates that the product is added to the wishlist successfully
    And user removes the product from the wishlist on the product details page
    Then user validates that the product is removed from the wishlist successfully

  @sanity @TC017 @TC024 @book
  Scenario: Verify adding/removing products from Wishlist page (You Might Also Like)

    Given user navigates back to the Home Page
    And user clicks on the Wishlist icon
    When user adds a product to the wishlist from the You Might Also Like section
    And user validates that the product is added to the wishlist successfully
    Then user removes the product from the wishlist in the You Might Also Like section
    And user validates that the product is removed from the wishlist successfully
#    And user adds a product to the bag from the You Might Also Like section
#    And user selects the size

  @sanity @TC022
  Scenario: TES3858 - Verify adding/removing products from wishlist to bag/cart

    Given user navigates back to the Home Page
    When user clicks on the Wishlist icon
    And user validates the wishlisted products list
    And user adds a product to the bag from the wishlist
    And user is navigated to the Product Detail Page
    And user validates the out of stock product
    Then user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on View Bag

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality
    Given user navigates back to the Home Page
    When user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out