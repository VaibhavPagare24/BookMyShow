Feature: Events listing

  Background:
    Given I navigate to BookMyShow application
    And I select a valid city

  @regression
  Scenario: Weekend events with price filter
    When I click on Events tab
    And I apply weekend filter for events
    And I apply price range filter
    Then I should see filtered events
    And events should be displayed with their details
    And events should be sorted by price

  @smoke
  Scenario: All events without filters
    When I click on Events tab
    Then I should see all available events 