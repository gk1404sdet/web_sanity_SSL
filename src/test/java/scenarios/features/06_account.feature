@account
Feature: Verify the Account Module in the Application

@accountComponents
  Scenario: TES51 - Verify components of Account

    Given user is on the Home Page
    Given user navigation to my profile
    When system should display the following components in the Account section
      | My Profile             |
      | My Orders              |
      | My Wallet              |
      | Gift Card/EGV          |
      | First Citizen Club     |
      | Stores And Events      |
      | Book a Personal Shopper|
      | Logout                 |

@accountCRUD
  Scenario: TES53 - Verify CRUD on the Address page

    Given user clicks on the my Profile option
    When user updates their personal details
    And user update their name
    And user update their gender details
    And user clicks on the update changes
    And user validate that personal details successfully updated
    And user is able to add a new address
    And user enters the new name
    And user enters the new number
    And user enters the new pin code
    And user enters the new address
    And user selects a address type as work
    And user clicks on the add address
    And user validate that new address added successfully
    Then user updates an existing address
    And user updates their name
    And user updates their number
    And user updates their pin code
    And user updates their address
    And user clicks on the update changes
    And validate that existing address has updated
    And user is able to delete exiting address
    And user clicks on the confirm button for delete address
    And user validate that delete address message is displayed

@bookAnAppointment
  Scenario: TES74 - Verify the Book an Appointment Functionality

    Given user clicks on Book A Personal Shopper
    When user clicks on the book an appointment
    And user selects the city
    And user selects the store
    And user validate that get store directions option is displayed
    And user select the date
    And user clicks on the select time
    Then user clicks on the book a service button
    And user validate that personal details
    And user clicks on the proceed to book button
    And user validate that booking confirmation is displayed

@myOrder
  Scenario: TES69 - Verify navigation and functionality of My Order

    Given user clicks on My Orders
    When user is able to see the existing orders
    And user applies the filter option
    And user applies filter Past 30 days
    And user is able to see the filtered orders accordingly
    And user applies the filter option
    And user applies filter Past 6 Months
    And user is able to see the filtered orders accordingly
    And user applies the filter option
    And user applies filter Completed
    Then user is able to see the filtered orders accordingly
    And user applies the filter option
    And user applies filter Cancelled
    And user is able to see the filtered orders accordingly
    And user applies the filter option
    And user applies filter Past 1 Year
    And user clicks on an existing order to view details
    Then user validates the order details successfully

@myWallet
  Scenario: TES71 - Verify navigation and functionality of My Wallet

    When user clicks the my Wallet
    And user validates that wallet account details

@giftCard
   Scenario: TES72 - Verify navigation and functionality of GC/EGV

     When user clicks the My Gift card or EGV
     And user validates that gift vouchers product list
