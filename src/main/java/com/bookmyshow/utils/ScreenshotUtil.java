package com.bookmyshow.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots
 */
public class ScreenshotUtil {

	private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

	/**
	 * Take screenshot and save to specified path
	 * 
	 * @param driver   WebDriver instance
	 * @param testName Test name for screenshot filename
	 * @return Screenshot file path
	 */
	public static String takeScreenshot(WebDriver driver, String testName) {
		try {
			// Create screenshots directory if it doesn't exist
			File screenshotDir = new File(ConfigReader.getScreenshotPath());
			if (!screenshotDir.exists()) {
				screenshotDir.mkdirs();
			}

			// Generate timestamp for unique filename
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
			String fileName = testName + "_" + timestamp + ".png";
			String filePath = ConfigReader.getScreenshotPath() + fileName;

			// Take screenshot
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			File destFile = new File(filePath);

			// Copy screenshot to destination
			FileUtils.copyFile(sourceFile, destFile);

			logger.info("Screenshot saved: {}", filePath);
			return filePath;

		} catch (IOException e) {
			logger.error("Failed to take screenshot: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Take screenshot with current timestamp
	 * 
	 * @param driver WebDriver instance
	 * @return Screenshot file path
	 */
	public static String takeScreenshot(WebDriver driver) {
		return takeScreenshot(driver, "screenshot");
	}
}
