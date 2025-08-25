Feature: Sports events

  Background:
    Given I navigate to BookMyShow application
    And I select a valid city

  @regression
  Scenario: Weekend sports events
    When I click on Sports tab
    And I apply weekend filter
    Then I should see available sports events
    And sports events should be displayed with their prices
    And sports events should be sorted by price 