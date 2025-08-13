package com.bookmyshow.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bookmyshow.utils.ConfigReader;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TestNG listener for ExtentReports integration
 */
public class ExtentReportListener implements ITestListener {

	private static final Logger logger = LogManager.getLogger(ExtentReportListener.class);
	private static ExtentReports extentReports;
	private static ExtentTest extentTest;

	/**
	 * Initialize ExtentReports before test suite starts
	 */
	@Override
	public void onStart(org.testng.ITestContext context) {
		try {
			// Create reports directory if it doesn't exist
			File reportDir = new File(ConfigReader.getExtentReportPath());
			if (!reportDir.exists()) {
				reportDir.mkdirs();
			}

			// Generate timestamped report file name
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
			String reportPath = ConfigReader.getExtentReportPath() +"ExtendReport_"+ timestamp + ".html";

			// Initialize ExtentSparkReporter
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

			// Configure report settings
			sparkReporter.config().setDocumentTitle("Online Shopping Automation Report");
			sparkReporter.config().setReportName("Flipkart Automation Test Results");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

			// Initialize ExtentReports
			extentReports = new ExtentReports();
			extentReports.attachReporter(sparkReporter);

			// Set system information
			extentReports.setSystemInfo("Application", "Flipkart Automation Test");
			extentReports.setSystemInfo("Environment", "Test");
			extentReports.setSystemInfo("Browser", ConfigReader.getBrowser());
			extentReports.setSystemInfo("OS", System.getProperty("os.name"));
			extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
			extentReports.setSystemInfo("User", System.getProperty("user.name"));

			logger.info("ExtentReports initialized successfully: {}", reportPath);

		} catch (Exception e) {
			logger.error("Failed to initialize ExtentReports: {}", e.getMessage());
		}
	}

	/**
	 * Create test entry when test starts
	 */
	@Override
	public void onTestStart(ITestResult result) {
		try {
			String testName = result.getMethod().getMethodName();
			String testDescription = result.getMethod().getDescription();

			extentTest = extentReports.createTest(testName, testDescription);
			extentTest.info("Test started: " + testName);

			logger.info("ExtentTest created for: {}", testName);

		} catch (Exception e) {
			logger.error("Failed to create ExtentTest: {}", e.getMessage());
		}
	}

	/**
	 * Update test status when test passes
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		try {
			extentTest.log(Status.PASS, "Test passed successfully");
			logger.info("Test passed: {}", result.getMethod().getMethodName());
		} catch (Exception e) {
			logger.error("Failed to log test success: {}", e.getMessage());
		}
	}

	/**
	 * Update test status when test fails
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		try {
			Throwable throwable = result.getThrowable();
			extentTest.log(Status.FAIL, "Test failed");

			if (throwable != null) {
				extentTest.log(Status.FAIL, throwable.getMessage());
			}

			logger.error("Test failed: {}", result.getMethod().getMethodName());

		} catch (Exception e) {
			logger.error("Failed to log test failure: {}", e.getMessage());
		}
	}

	/**
	 * Update test status when test is skipped
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		try {
			extentTest.log(Status.SKIP, "Test was skipped");

			if (result.getThrowable() != null) {
				extentTest.log(Status.SKIP, result.getThrowable().getMessage());
			}

			logger.info("Test skipped: {}", result.getMethod().getMethodName());

		} catch (Exception e) {
			logger.error("Failed to log test skip: {}", e.getMessage());
		}
	}

	/**
	 * Finalize report when test suite finishes
	 */
	@Override
	public void onFinish(org.testng.ITestContext context) {
		try {
			if (extentReports != null) {
				extentReports.flush();
				logger.info("ExtentReports flushed successfully");
			}
		} catch (Exception e) {
			logger.error("Failed to flush ExtentReports: {}", e.getMessage());
		}
	}

	/**
	 * Get current ExtentTest instance
	 * 
	 * @return Current ExtentTest instance
	 */
	public static ExtentTest getExtentTest() {
		return extentTest;
	}

	/**
	 * Add screenshot to current test
	 * 
	 * @param screenshotPath Path to screenshot file
	 */
	public static void addScreenshot(String screenshotPath) {
		try {
			if (extentTest != null && screenshotPath != null) {
				extentTest.addScreenCaptureFromPath(screenshotPath);
				logger.info("Screenshot added to report: {}", screenshotPath);
			}
		} catch (Exception e) {
			logger.error("Failed to add screenshot to report: {}", e.getMessage());
		}
	}

	/**
	 * Log info message to current test
	 * 
	 * @param message Info message
	 */
	public static void logInfo(String message) {
		try {
			if (extentTest != null) {
				extentTest.info(message);
			}
		} catch (Exception e) {
			logger.error("Failed to log info message: {}", e.getMessage());
		}
	}

	/**
	 * Log pass message to current test
	 * 
	 * @param message Pass message
	 */
	public static void logPass(String message) {
		try {
			if (extentTest != null) {
				extentTest.pass(message);
			}
		} catch (Exception e) {
			logger.error("Failed to log pass message: {}", e.getMessage());
		}
	}

	/**
	 * Log fail message to current test
	 * 
	 * @param message Fail message
	 */
	public static void logFail(String message) {
		try {
			if (extentTest != null) {
				extentTest.fail(message);
			}
		} catch (Exception e) {
			logger.error("Failed to log fail message: {}", e.getMessage());
		}
	}
}