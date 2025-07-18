@login
Feature: User Login Functionality with Valid and Invalid Data

@mobileNoLogin
  Scenario: TES23 - Verify the Sign-in functionality via phone number for existing user

    Given user launches the application
    When user taps on the Login button
    And user enters mobile number "9136734340"
    And user clicks on the Proceed button
    Then user enters OTP "123456"
    And user clicks on the Verify OTP button
    Then system should display the appropriate login status

@logout
  Scenario: TES79 - Verify the logout functionality

    When user clicks on the Logout button
    And user clicks on the yes button for logout
    Then user validate that the user is logged out

@emailLogin
  Scenario Outline: TES25 - Verify the Sign-in functionality via Email ID for existing user

    Given user launches the application
    When user taps on the Login button
    And user enters email id "<email id>"
    And user clicks on the Proceed button
    Then user enters OTP

    Examples:
      | email id                             |
      | gopalkrishnan.sslconsultant@shoppersstop.com |

@OTPValidation
  Scenario: Verify the OTP functionality with an invalid OTP and Resend functionality

    Given user launches the application
    When user taps on the Login button
    And user enters mobile number "7173591927"
    And user clicks on the Proceed button
    Then user enters OTP "123456"
    And user clicks on the Verify OTP button for validation
    And user clicks the resend otp option
    And user has successfully resent the OTP
    Then user enters OTP "654321"
    And user clicks on the Verify OTP button for validation

@invalidMobileNoLogin
  Scenario: Verify the Sign-in functionality via phone number with an invalid number

    Given user launches the application
    When user taps on the Login button
    And user enters mobile number "979015373778"
    And user clicks the Proceed button for validation of invalidUid
    Then user validate that the appropriate error message is displayed

@invalidEmailIDLogin
  Scenario Outline: Verify the Sign-in functionality via phone number with an invalid email id

    Given user launches the application
    When user taps on the Login button
    And user enters the invalid email id "<email id>"
    And user clicks the Proceed button for validation of invalidUid
    Then user validate that the appropriate error message is displayed

    Examples:
      | email id                                      |
      | gopalakrishnan.palanisamy@mool.comok          |

