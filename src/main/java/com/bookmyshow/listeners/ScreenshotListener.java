package com.bookmyshow.listeners;

import com.bookmyshow.base.*;
import com.bookmyshow.utils.ConfigReader;
import com.bookmyshow.utils.ScreenshotUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TestNG listener for capturing screenshots on test events
 */
public class ScreenshotListener implements ITestListener {

	private static final Logger logger = LogManager.getLogger(ScreenshotListener.class);

	/**
	 * Capture screenshot when test fails
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		try {
			// Get the test instance
			Object testInstance = result.getInstance();

			if (testInstance instanceof DriverSetup) {
				DriverSetup baseTest = (DriverSetup) testInstance;

				if (baseTest.getDriver() != null) {
					String testName = result.getMethod().getMethodName();
					String screenshotPath = ScreenshotUtil.takeScreenshot(baseTest.getDriver(), testName + "_FAILED");

					if (screenshotPath != null) {
						// Add screenshot to ExtentReports
						ExtentReportListener.addScreenshot( ConfigReader.getExtentReportPathScreenshot()+ screenshotPath);

						// Log screenshot path
						logger.info("Screenshot captured for failed test: {}", screenshotPath);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Failed to capture screenshot on test failure: {}", e.getMessage());
		}
	}

//    @Override
//    public void onTestFailure(ITestResult result) {
//        Object testInstance = result.getInstance();
//        if (testInstance instanceof DriverSetup) {
//            WebDriver driver = ((DriverSetup) testInstance).getDriver();
//            if (driver != null) {
//                String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName() + "_FAILED");
//                ExtentReportListener.addScreenshot(screenshotPath);
//                logger.info("Screenshot captured for failed test: {}", screenshotPath);
//            }
//        }
//    }

	/**
	 * Capture screenshot when test is skipped
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		try {
			// Get the test instance
			Object testInstance = result.getInstance();

			if (testInstance instanceof DriverSetup) {
				DriverSetup baseTest = (DriverSetup) testInstance;

				if (baseTest.getDriver() != null) {
					String testName = result.getMethod().getMethodName();
					String screenshotPath = ScreenshotUtil.takeScreenshot(baseTest.getDriver(), testName + "_SKIPPED");

					if (screenshotPath != null) {
						// Add screenshot to ExtentReports
						ExtentReportListener.addScreenshot( ConfigReader.getExtentReportPathScreenshot() +screenshotPath);

						// Log screenshot path
						logger.info("Screenshot captured for skipped test: {}", screenshotPath);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Failed to capture screenshot on test skip: {}", e.getMessage());
		}
	}

	/**
	 * Optionally capture screenshot when test passes (for documentation)
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		try {
			// Only capture screenshot for passed tests if configured
//            boolean captureOnSuccess = Boolean.parseBoolean(
//                    System.getProperty("screenshot.on.success", "false"));

			boolean captureOnSuccess = true;

			if (captureOnSuccess) {
				Object testInstance = result.getInstance();

				if (testInstance instanceof DriverSetup) {
					DriverSetup baseTest = (DriverSetup) testInstance;

					if (baseTest.getDriver() != null) {
						String testName = result.getMethod().getMethodName();
						String screenshotPath = ScreenshotUtil.takeScreenshot(baseTest.getDriver(),
								testName + "_PASSED");

						if (screenshotPath != null) {
							// Add screenshot to ExtentReports
							ExtentReportListener.addScreenshot(ConfigReader.getExtentReportPathScreenshot()+screenshotPath);

							// Log screenshot path
							logger.info("Screenshot captured for passed test: {}", screenshotPath);
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("Failed to capture screenshot on test success: {}", e.getMessage());
		}
	}
}
