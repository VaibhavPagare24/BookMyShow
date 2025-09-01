Feature: Google Sign-In validation

  Background:
    Given I navigate to BookMyShow application
    And I select a valid city

  @regression
  Scenario: Google sign-in with invalid email
    When I click on Sign In button
    And I click on Continue with Google
    And I switch to Google authentication window
    And I enter invalid email "invaliduser12345@gmail.com"
    Then I should see appropriate error message for invalid email
    And the error message should contain expected authentication failure text
    When I switch back to main window
    Then I should be back on BookMyShow main page

  @regression
  Scenario: End-to-end invalid sign-in
    When I attempt to sign in with invalid email "invaliduser12345@gmail.com"
    Then I should see appropriate error message for invalid email
    And the error message should contain expected authentication failure text 