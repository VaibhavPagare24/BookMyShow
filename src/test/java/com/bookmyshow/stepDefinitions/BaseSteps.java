package com.bookmyshow.stepDefinitions;

import org.openqa.selenium.WebDriver;

public class BaseSteps {
	private static WebDriver driver;
	
	public static void setDriver(WebDriver webDriver) {
		driver = webDriver;
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
} 