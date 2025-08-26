package com.bookmyshow.hooks;

import com.bookmyshow.base.DriverSetup;
import com.bookmyshow.stepDefinitions.BaseSteps;
import com.bookmyshow.utils.ConfigReader;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class TestHooks extends DriverSetup {
	@Before
	public void beforeScenario() {
		String browser = ConfigReader.getProperty("browser");
		initializeDriver(browser);
		navigateToApplication();
		BaseSteps.setDriver(getDriver());
	}
	
	@After
	public void afterScenario() {
		WebDriver driver = getDriver();
		if (driver != null) {
			driver.quit();
		}
	}
} 