package com.bookmyshow.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"com.bookmyshow.stepDefinitions", "com.bookmyshow.hooks"},
		plugin = {
			"pretty",
			"html:target/cucumber-html-reports/index.html",
			"json:target/cucumber-json-reports/Cucumber.json",
			"junit:target/cucumber-xml-reports/Cucumber.xml",
			"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
		},
		monochrome = true,
		dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
} 