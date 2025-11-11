Feature: Verify the Account Module in the Application

  @smoke @sanity @TC002
  Scenario: Verify the sign-in functionality via phone number for an existing user
    Given user launches the application
    When user taps on the Login button
    And user enters the valid mobile number
    And user clicks on the Proceed button
    Then user enters the OTP
    And user clicks on the Verify OTP button
    Then system should display the appropriate login status

  @smoke @sanity @TC045
  Scenario: Verify components of Account
    Given user navigates to My Profile
    Then system should display the following components in the Account section
      | My Profile              |
      | My Orders               |
      | My Wallet               |
      | Gift Card/EGV           |
      | First Citizen Club      |
      | Stores And Events       |
      | Book a Personal Shopper |
      | Logout                  |

  @sanity @TC031 @TC046
  Scenario: Verify CRUD on the Address page
    Given user navigates back to the Home Page
    And user is on the Home Page
    When user clicks on My Profile option
    And user updates personal details
    And user updates name
    And user updates gender details
    And user clicks on Update Changes
    And user validates that personal details are updated successfully
    And user adds a new address
    And user enters new name
    And user enters new number
    And user enters new pin code
    And user enters new address
    And user selects address type as Work
    And user clicks on Add Address
    And user validates that new address is added successfully
    Then user updates an existing address
    And user updates name
    And user updates number
    And user updates pin code
    And user updates address
    And user clicks on Update Changes
    And validate that existing address is updated
    And user deletes existing address
    And user clicks on Confirm button to delete address
    And user validates that delete confirmation message is displayed

  @sanity @TC051
  Scenario: Verify Book an Appointment functionality
    Given user navigates back to the Home Page
    And user is on the Home Page
    Given user clicks on Book a Personal Shopper
    When user clicks on Book an Appointment
    And user selects city
    And user selects store
    And user validates that Get Store Directions option is displayed
    And user selects date
    And user clicks on Select Time
    Then user clicks on Book a Service button
    And user validates personal details
    And user clicks on Proceed to Book button
    And user validates that booking confirmation is displayed

  @sanity @TC041 @TC047
  Scenario: Verify navigation and functionality of My Orders
    Given user navigates back to the Home Page
    And user is on the Home Page
    Given user clicks on My Orders
    When user views existing orders
    And user applies filter option
    And user applies filter Past 30 Days
    And user views filtered orders accordingly
    And user applies filter option
    And user applies filter Past 6 Months
    And user views filtered orders accordingly
    And user applies filter option
    And user applies filter Completed
    Then user views filtered orders accordingly
    And user applies filter option
    And user applies filter Cancelled
    And user views filtered orders accordingly
    And user applies filter option
    And user applies filter Past 1 Year
    And user clicks on an existing order to view details
    Then user validates order details successfully

  @smoke @sanity @TC048
  Scenario: Verify navigation and functionality of My Wallet
    Given user navigates back to the Home Page
    And user is on the Home Page
    When user clicks on My Wallet
    And user validates wallet account details

  @smoke @sanity @TC052
  Scenario: Verify components of FCC page
    Given user navigates back to the Home Page
    And user is on the Home Page
    When user clicks on First Citizen Club
    And system should display the following components in the FCC section
      | Silver Edge                   |
      | Golden Glow                   |
      | Platinum Aura                 |
      | Black Tier                    |
      | Membership Details & Benefits |
    Then user clicks on Card
    And user validates card details

  @smoke @sanity @TC049
  Scenario: Verify navigation and functionality of GC/EGV
    Given user navigates back to the Home Page
    And user is on the Home Page
    When user clicks on My Gift Card or EGV
    And user validates gift voucher product list

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality
    Given user navigates back to the Home Page
    And user is on the Home Page
    When user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out