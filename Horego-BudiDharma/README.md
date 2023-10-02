# Automation Test with Selenium and Cucumber

### Requirement
- JDK 17
- Google Chrome

### How To Run
- Clone this project
- Import as **Maven project** in e.g. IntelliJ or Eclipse.
- Run command in terminal
  ``mvn clean test`` or ``mvn test``

- to run single case/feature example using tags
  - mvn test -Dcucumber.filter.tags="@tagname"

Structure project
- Feature cucumber scenario test case
  - src/test/java/Features/case.feature

- Setup Runner for cucumber 
  - src/test/java/Runner/TestRunner.java

- Step definition (method for testcase)
  - src/test/java/StepDefinition/casePage.java

- report location
  - target/cucumber-reports.html