@Web_test
Feature: test case web

    @check_login
    Scenario: Login case
        Given User do login step
        Then User already in login stage

    @create_new_user
    Scenario: User create new user
        Given User do login step
        And User already in login stage
        And User go to admin add user page
        And User create new user
        Then Search user added


