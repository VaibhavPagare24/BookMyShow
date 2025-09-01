package com.bookmyshow.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.bookmyshow.utils.AllureReportOpener;

@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"com.bookmyshow.stepDefinitions", "com.bookmyshow.hooks"},
		plugin = {
			"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
			"pretty",
			"html:target/cucumber-html-reports/index.html",
			"json:target/cucumber-json-reports/Cucumber.json",
			"junit:target/cucumber-xml-reports/Cucumber.xml"
		},
		monochrome = false,
		dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
	@BeforeSuite
	public void beforeSuite() {
		AllureReportOpener.cleanAllureResults();
	}
	
	@AfterSuite
	public void afterSuite() {
		AllureReportOpener.openAllureReport();
	}
}