# Cucumber BDD for BookMyShow (Java 11+, Selenium 4, TestNG)

## How to Run

- IDE (TestNG):
  - Right-click and Run any runner:
    - `com.bookmyshow.runners.TestRunner` (all features)
    - `com.bookmyshow.runners.SmokeTestRunner` (@smoke)
    - `com.bookmyshow.runners.RegressionTestRunner` (@regression)
    - `com.bookmyshow.runners.LoginTestRunner` (@login)

- Maven:
  - `mvn clean test` (executes `cucumber.xml` via Surefire)

## Reports
- HTML: `target/cucumber-html-reports/index.html`
- JSON: `target/cucumber-json-reports/Cucumber.json`
- JUnit XML: `target/cucumber-xml-reports/Cucumber.xml`

Smoke/Regression/Login runs also write under subfolders: `smoke/`, `regression/`, `login/`.

## Tags
- @smoke: quick checks on movies/events
- @regression: full coverage scenarios
- @login: login-only scenarios

## Configuration
- Set browser and app URL in `src/main/resources/config.properties`.
- Excel test data path key: `test.data.file` (sheet: `SearchData`, column 0).

## JSON Utilities
- `com.bookmyshow.utils.JsonUtil` provides simple helpers:
  - Read to a class:
    ```java
    TestData data = JsonUtil.read("src/test/resources/data/test.json", TestData.class);
    ```
  - Read to a list/map:
    ```java
    List<TestData> list = JsonUtil.read("src/test/resources/data/list.json", new TypeReference<List<TestData>>(){});
    ```
  - Write object:
    ```java
    JsonUtil.write("target/out/result.json", data);
    ```
  - Append element to JSON array file:
    ```java
    JsonUtil.appendToArray("target/out/list.json", dataItem, new TypeReference<List<TestData>>(){});
    ```

## Retry on Failure
- Automatic retry is enabled for TestNG tests and Cucumber runners via `com.bookmyshow.listeners.RetryListener`.
- Default max retries per failed test: 1 (configurable in `RetryAnalyzer`).
- Registered in both `testng.xml` and `cucumber.xml`.

## Notes
- Hooks initialize the driver per scenario using `DriverSetup` and `ConfigReader`.
- Steps map directly to POM methods: `HomePage`, `MoviesPage`, `SportsPage`, `EventsPage`, `LoginPage`.