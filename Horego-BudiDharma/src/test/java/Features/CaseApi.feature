@Api_test
Feature: test case api

  @bearer_token_case
  Scenario: bearer token case
    Given User want to create account in api
    And User input param for create account in api
    Then User verify body respond from create account api
    And User want to login account in api
    And User input param for login account in api
    Then User verify body respond from login account api
    And User want to forgot password in api
    And user input param for forgot password in api
    Then User verify body respond from forgot password in api
    And User want to reset password in api
    And user input param for reset password in api
    Then User verify body respond from reset password in api


