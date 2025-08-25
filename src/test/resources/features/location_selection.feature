Feature: Location selection on BookMyShow

  Background:
    Given I navigate to BookMyShow application

  @smoke @regression
  Scenario: Select location from Excel data
    When I select city from Excel data
    Then the location should be updated successfully

  @regression
  Scenario Outline: Select specific city
    When I select city "<city>"
    Then the location should be updated successfully

    Examples:
      | city      |
      | Mumbai    |
      | Coimbatore|