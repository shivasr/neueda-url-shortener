Feature: Generate Short URLs
  Scenario: Create a new short URL
    Given I am an API user "user"
    When I hit the API at "/"
    And send JSON containing url "https://www.google.com/search?q=hello"
    Then generate a short URL "http://localhost/3ibJF44"

  Scenario: I access the short URL
    Given I am any internet user
    When I enter the URL "https://bit.ly/3ibJF44" in the browser
    Then redirect me to "https://www.google.com/search?q=hello"

