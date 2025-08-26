package com.bookmyshow.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"com.bookmyshow.stepDefinitions", "com.bookmyshow.hooks"},
		tags = "@smoke",
		plugin = {
			"pretty",
			"html:target/cucumber-html-reports/smoke/index.html",
			"json:target/cucumber-json-reports/smoke/Cucumber.json",
			"junit:target/cucumber-xml-reports/smoke/Cucumber.xml"
		},
		monochrome = true,
		dryRun = false
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
} 