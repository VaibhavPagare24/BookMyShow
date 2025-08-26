Feature: Movies languages

  Background:
    Given I navigate to BookMyShow application
    And I select a valid city

  @smoke @regression
  Scenario: View available movie languages
    When I click on Movies tab
    Then I should see all available movie languages