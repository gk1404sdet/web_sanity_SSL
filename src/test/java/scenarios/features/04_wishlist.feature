@wishlist
Feature: Verify the Wishlist Functionality

@wishlistPLP
  Scenario: TES37 - Verify adding/removing products from PLP

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

@wishlistPDP
  Scenario: TES38 - Verify adding/removing products from PDP

    Given user clicks on the kids category
    When user selects a sub-category under kids
    And user selects the product from the product listing page
    And user is navigated to the product detail page
    And user add a product to the wishlist from the product details page
    Then user validates that product add wishlist successfully
    And user removes the product from the wishlist from the product details page
    Then user validates that product removed wishlist successfully

@wishlistToBag
  Scenario: Verify adding/removing products from wishlist to bag/cart

    Given user back to Home Page
    When user clicks on the Wishlist icon
    When user validates the wish listed products list
    And user adda a product to bag from wishlist
    And user is navigated to the product detail page
    And user validating the out of stock product
    Then user selects a size if available
    And user clicks on Add to Bag and View Bag

@wishlistYouMightLike
  Scenario: TES39 - Verify adding/removing products from Wishlist page (You Might also Like)

    Given user back to Home Page
    And user clicks on the Wishlist icon
    When user add a product to wishlist from you might also like section
    And user validates that product add wishlist successfully
    Then user removes the product to wishlist from you might also like section
    And user validates that product removed wishlist successfully

@addToCartYouMightLike
  Scenario: TES47 - Verify adding/removing products from Wishlist page to cart/bag (You Might also Like)

    When user add a product to add to bag from you might also like section
    And user selects the size
