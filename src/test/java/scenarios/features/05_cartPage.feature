@cart
Feature: Verify the Cart Page Functionality

@cartPageComponents
  Scenario: TES40 - Verify the components of Bag

    Given user is on the Home Page
    When user clicks on the Cart icon
    When system should display the following components in the cart page
      | Offers for you         |
      | Check Delivery         |
      | GIFT WRAP              |
      | HAVE A COUPON CODE?    |
      | Price Details          |

@invalidPincode
  Scenario: Verify functionality of Pincode check on the cart page

    Given user clicks the pincode check box
    When user enters the invalid pincode
    Then user clicks the check option to validate the pincode
    And system should display the appropriate error status

@validPincode
  Scenario: TES43 - Verify functionality of Pincode check on the cart page

    Given user clicks the pincode check box
    When user enters the pincode
    Then user clicks the check option to validate the pincode

@quantityAdjust
  Scenario: TES48 - Verify increase and decrease of the product quantity

    When user increase the quantity from the cart
    Then user decrease the quantity from the cart

@giftWrap
  Scenario: TES49 - Verify adding/removing coupons and Gift Wrap message

    When user clicks gift wrap option
    And user enters the receiver's name
    And user enters the gift message
    And user enters the sender's name
    Then user clicks on the save gift message
    And user validate that gift wrap details added successfully
    And user clicks gift wrap option
    And user validate that gift wrap details removed successfully

@sizeChart
  Scenario: TES45 - Verify functionality of size changing on the cart page

    Given user clicks the size changing option
    When user validate that available size

@moveToWishlist
  Scenario: TES42 - Verify adding/removing product to bag from PDP and Move to Wishlist

    When user clicks on the xmark icon
    And user is moving the product to wishlist from bag
    And user validates that product moved to wishlist from bag
    When user clicks on the xmark icon
    Then user is removing the product from bag
    And user validates that product removed from bag
    And user is on the Home Page

#@cartYouMayLike @cart
#  Scenario: TES1202 - Verify adding/removing product to bag from You May Also Like on the Bag page
#
#    Given user add a product to the bag from you may also like
#    When user clicks on the add to bag
#    And user selects the size
#    Then user validates that product add to cart successfully
