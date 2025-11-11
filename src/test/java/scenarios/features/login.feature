Feature: User Login Functionality with Valid and Invalid Data

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

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality
    When user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out

  @smoke @sanity @TC003
  Scenario Outline: Verify the sign-in functionality via email ID for an existing user
    Given user launches the application
    When user taps on the Login button
    And user enters the email ID "<email id>"
    And user clicks on the Proceed button
    Then user enters the OTP

    Examples:
      | email id                                   |
      | gopalkrishnan.sslconsultant@shoppersstop.com |

  @smoke @sanity @TC065
  Scenario Outline: Verify the OTP functionality with an invalid OTP and resend functionality
    Given user launches the application
    When user taps on the Login button
    And user enters the mobile number "<mob no>"
    And user clicks on the Proceed button
    Then user enters the OTP "<otp>"
    And user clicks on the Verify OTP button for validation
    And user clicks on the Resend OTP option
    And user successfully resends the OTP
    Then user enters the OTP "654321"
    And user clicks on the Verify OTP button for validation

    Examples:
      | mob no     | otp    |
      | 9876457125 | 123456 |

  @sanity @TC065
  Scenario Outline: Verify the sign-in functionality via phone number with an invalid number
    Given user launches the application
    When user taps on the Login button
    And user enters the mobile number "<mob no>"
    And user clicks on the Proceed button to validate the invalid user ID
    Then validate that the appropriate error message is displayed

    Examples:
      | mob no        |
      | 979015373778  |

  @sanity @TC065
  Scenario Outline: Verify the sign-in functionality via phone number with an invalid email ID
    Given user launches the application
    When user taps on the Login button
    And user enters the invalid email ID "<email id>"
    And user clicks on the Proceed button to validate the invalid user ID
    Then validate that the appropriate error message is displayed

    Examples:
      | email id                                     |
      | gopalakrishnan.palanisamy@mool.comok         |

  @smoke @sanity @TC021
  Scenario: Verify the functionality of merging the cart from guest user to logged-in user
    Given user launches the application
    When user clicks on the Men category
    And user selects a sub-category under Men
    And user selects a product from the PLP
    And user is navigated to the Product Detail Page
    And user validates the out of stock product
    Then user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on View Bag
    And user clicks on Place Order
    And user validates that a guest user is prompted to enter login credentials
    And user enters the mobile number "9136734340"
    And user clicks on the Proceed button
    Then user enters the OTP "123456"
    And user clicks on the Verify OTP button
    And user validates the price details in the bag
    Then user navigates back to the Home Page

  @smoke @sanity @TC055
  Scenario: Verify the logout functionality
    When user clicks on the Logout button
    And user clicks on the Yes button to confirm logout
    Then validate that the user is logged out