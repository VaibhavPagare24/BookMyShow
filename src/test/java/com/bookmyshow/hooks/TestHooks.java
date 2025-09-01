package com.bookmyshow.hooks;

import com.bookmyshow.base.DriverSetup;
import com.bookmyshow.stepDefinitions.BaseSteps;
import com.bookmyshow.utils.ConfigReader;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class TestHooks extends DriverSetup {
	@Before
	public void beforeScenario() {
		String browser = ConfigReader.getProperty("browser");
		initializeDriver(browser);
		navigateToApplication();
		BaseSteps.setDriver(getDriver());
	}
	
	@After
	public void afterScenario(Scenario scenario) {
		WebDriver driver = getDriver();
		try {
			if (scenario.isFailed() && driver instanceof TakesScreenshot) {
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
			}
		} catch (Exception ignored) {}
		
		if (driver != null) {
			driver.quit();
		}
	}
} 