package com.bookmyshow.utils;

import java.util.Set;

import org.openqa.selenium.WebDriver;

/**
 * Utility class for browser window and tab management
 * Provides methods to switch between multiple browser tabs/windows
 */
public class BrowserHelpers {

	// Static WebDriver instance shared across all helper methods
	static WebDriver driver;

	/**
	 * Constructor to initialize BrowserHelpers with WebDriver
	 * @param driver WebDriver instance to control browser windows
	 */
	public BrowserHelpers(WebDriver driver) {
		BrowserHelpers.driver = driver; // Store driver reference statically
	}

	/**
	 * Switches to a newly opened browser tab
	 * @param oldHandles Set of window handles before new tab was opened
	 */
	public static void switchToNewTab(Set<String> oldHandles) {
		// Get current window handles after new tab opened
		Set<String> newHandles = driver.getWindowHandles();
		
		// Find the new tab handle and switch to it
		for (String handle : newHandles) {
			if (!oldHandles.contains(handle)) { // This is the new tab
				driver.switchTo().window(handle); // Switch to new tab
				break; // Exit once new tab is found
			}
		}
	}

	/**
	 * Switches to the first available browser tab (usually the original tab)
	 * Used to return to main tab after working with secondary tabs
	 */ 	
	public static void switchToOriginalTab() {
		// Get all available window handles
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle); // Switch to first available tab
			break; // Exit after switching to first tab
		}
	}

}
