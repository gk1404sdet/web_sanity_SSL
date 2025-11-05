Feature: Verify the Header Functionality

  Background:
  @smoke @sanity @TC002
  Scenario Outline: Verify the sign-in functionality via phone number for an existing user
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

  @smoke @sanity @TC006 @TC010
  Scenario: Verify navigation and functionality of all top-level homepage categories

    Given user launches the application
    And user is on the Home Page
    Then system should display the following components in the Top Categories section
      | MEN        |
      | WOMEN      |
      | KIDS       |
      | BEAUTY     |
      | WATCHES    |
      | GIFTS      |
      | BRANDS     |
      | HOMESTOP   |
    And user verifies navigation for each category

  @sanity @TC008
  Scenario: Verify the Search Functionality

    Given user is on the Home Page
    And user clicks on the search bar
    When user validates Trending Search suggestions
    And user validates that from Trending Search suggestions landing the relevant products
    And user clicks on the search bar
    Then user validates Popular Brands suggestions
    And user validates that from Popular Brands suggestions landing the relevant products
    And user clicks on the search bar
    And user searches with dynamic keywords
    And user is on the Home Page

  @sanity @TC050
  Scenario: TES3886 - Verify the Store and Events Functionality

    When user clicks on the Store and Events icon
    And user clicks on the dropdown option to select city
    And user selects the city in the stores list
    And user validates that Get Store Directions option is displayed
    And user is on the Home Page

  @sanity @TC007
  Scenario: Verify navigation and functionality of Fashion Stylist

    When user clicks on the Fashion Stylist
    Then user enters the query
    And user validates the suggested product list

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user switches back to the main window
    And user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out