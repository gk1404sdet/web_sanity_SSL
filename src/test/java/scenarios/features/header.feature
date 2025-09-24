Feature: Verify the Header Functionality

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

@smoke @sanity @TC006 @TC010
  Scenario: TES3842 - User verifies navigation and functionality of all top-level homepage categories

    Given user launches the application
    Given user is on the Home Page
    When system should display the following components in the Top Categories section
      | MEN                    |
      | WOMEN                  |
      | KIDS                   |
      | BEAUTY                 |
      | WATCHES                |
      | GIFTS                  |
      | BRANDS                 |
      | HOMESTOP               |
    And verifying the each category navigation

@sanity @TC008
  Scenario: TES3844 - Verify the Search Functionality

    Given user is on the Home Page
    Given user clicks on the search bar
    When user validates that Trending Search suggestion
    And user validates that from Trending Search suggestion landing the relevant products
    And user clicks on the search bar
    And user validates that Trending Products suggestion
    Then user validates that Popular Brands
    And user validates that from Popular Brands suggestion landing the relevant products
    And user clicks on the search bar
    And user search with dynamic keywords
    And user is on the Home Page

@sanity @TC050
  Scenario: TES3886 - Verify the Store and Events Functionality

    When user clicks on Store and Events icon
    When user clicks on the drop down option for select city
    And user selects the city in the stores
    And user validate that get store directions option is displayed
    And user is on the Home Page

@sanity @TC007
  Scenario: TES3843 - Verify navigation and functionality of Fashion Stylist

    When user clicks the Fashion Stylist
    Then user enters the query
    And user validating the suggested product list

@smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When  user back to normal windows
    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out