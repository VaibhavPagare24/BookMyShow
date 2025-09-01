package com.bookmyshow.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;
import com.bookmyshow.listeners.RetryListener;

@Listeners(RetryListener.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"com.bookmyshow.stepDefinitions", "com.bookmyshow.hooks"},
		tags = "@regression",
		plugin = {
			"pretty",
			"html:target/cucumber-html-reports/regression/index.html",
			"json:target/cucumber-json-reports/regression/Cucumber.json",
			"junit:target/cucumber-xml-reports/regression/Cucumber.xml"
		},
		monochrome = true,
		dryRun = false
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {
} 