Feature: User Log in access control
  Calling the main site URl should give me the login page
  If I have signed up I can log in with my credentials
  If I have signed up but I get my password wrong I won't get into the system
  If I haven't signed up I won't be let into the system
  
  Scenario:
    Given I need to to login
    When I call the login page on my browser
    Then It should display it