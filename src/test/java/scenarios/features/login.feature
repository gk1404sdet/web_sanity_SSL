Feature: User Login Functionality with Valid and Invalid Data

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

@smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out

@smoke @sanity @TC003 @login
  Scenario Outline: Verify the Sign-in functionality via Email ID for existing user

    Given user launches the application
    When user taps on the Login button
    And user enters email id "<email id>"
    And user clicks on the Proceed button
    Then user enters OTP

    Examples:
      | email id                             |
      | gopalkrishnan.sslconsultant@shoppersstop.com |

@smoke @sanity @TC004 @TC065
  Scenario Outline: Verify the OTP functionality with an invalid OTP and Resend functionality

    Given user launches the application
    When user taps on the Login button
    And user enters mobile number "<mob no>"
    And user clicks on the Proceed button
    Then user enters OTP "<otp>"
    And user clicks on the Verify OTP button for validation
    And user clicks the resend otp option
    And user has successfully resent the OTP
    Then user enters OTP "654321"
    And user clicks on the Verify OTP button for validation

  Examples:
  | mob no | otp |
  | 9790153971 | 123456 |

@sanity @TC065
  Scenario Outline: Verify the Sign-in functionality via phone number with an invalid number

    Given user launches the application
    When user taps on the Login button
    And user enters mobile number "<mob no>"
    And user clicks the Proceed button for validation of invalidUid
    Then user validate that the appropriate error message is displayed

  Examples:
  | mob no |
  | 979015373778 |

@sanity @TC065
  Scenario Outline: Verify the Sign-in functionality via phone number with an invalid email id

    Given user launches the application
    When user taps on the Login button
    And user enters the invalid email id "<email id>"
    And user clicks the Proceed button for validation of invalidUid
    Then user validate that the appropriate error message is displayed

    Examples:
      | email id                                      |
      | gopalakrishnan.palanisamy@mool.comok          |

@smoke @sanity @TC021
  Scenario: Verify functionality of Merge cart as a guest to logged-in user

    Given user launches the application
    When user clicks on the Women category
    And user selects a sub-category under Women
    And user selects a product from the PLP
    And user is navigated to the product detail page
    And user validating the out of stock product
    Then user selects a size if available
    And user clicks on Add to Bag and View Bag
    And user clicks on the View Bag
    And user validates the price details in the bag
    And user clicks on Place Order
    And user validates that a guest user is prompted to enter login credentials
    And user enters mobile number "9136734340"
    And user clicks on the Proceed button
    Then user enters OTP "123456"
    And user clicks on the Verify OTP button
    And user validates the price details in the bag
    Then user back to Home Page

@smoke @sanity @TC055
  Scenario: Verify the logout functionality

    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out





