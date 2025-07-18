@header
Feature: Verify the Header Functionality

@category
  Scenario: TES28 - User verifies navigation and functionality of all top-level homepage categories

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

@search
  Scenario: TES29 - Verify the Search Functionality

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

@storeAndEvents
  Scenario: TES73 - Verify the Store and Events Functionality

    When user clicks on Store and Events icon
    When user clicks on the drop down option for select city
    And user selects the city in the stores
    And user validate that get store directions option is displayed
    And user is on the Home Page

@fashionStylist
  Scenario: TES27 - Verify navigation and functionality of Fashion Stylist

    When user clicks the Fashion Stylist
    Then user enters the query
    And user validating the suggested product list
