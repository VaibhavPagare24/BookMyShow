package com.bookmyshow.base;

import com.bookmyshow.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Base class for all test classes Contains WebDriver initialization and common
 * utilities
 */

public class DriverSetup {

	// Logger instance for logging driver setup activities
	private static final Logger logger = LogManager.getLogger(DriverSetup.class);
	
	// WebDriver instance to control browser - protected so child classes can access
	protected WebDriver driver;
	
	// WebDriverWait instance for explicit waits - shared across all page objects
	protected WebDriverWait wait;

	/**
	 * Initialize WebDriver based on browser configuration
	 * 
	 * @param browserName Browser name (chrome/firefox)
	 */
	public void initializeDriver(String browserName) {
		try {
			logger.info("Initializing WebDriver for browser: {}", browserName);

					// Switch statement to initialize different browser types based on configuration
		switch (browserName.toLowerCase()) {
		case "chrome":
			// Initialize Chrome browser with default settings
//                    WebDriverManager.chromedriver().setup();
//                    ChromeOptions chromeOptions = new ChromeOptions();
//                    chromeOptions.addArguments("--disable-notifications");
//                    chromeOptions.addArguments("--disable-popup-blocking");
//                    chromeOptions.addArguments("--start-maximized");
			driver = new ChromeDriver();
			driver.manage().window().maximize(); // Maximize browser window for better element visibility
			break;

		case "firefox":
			// Setup Firefox driver using WebDriverManager
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--disable-notifications"); // Disable browser notifications
			driver = new FirefoxDriver(firefoxOptions);
			driver.manage().window().maximize(); // Maximize browser window
			break;

		case "edge":
			// Setup Edge driver with hardcoded path (Note: should use WebDriverManager in production)
			System.setProperty("webdriver.edge.driver",
					"C:\\Users\\2421386\\Downloads\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
			driver.manage().window().maximize(); // Maximize browser window
			break;

		default:
			// Throw exception if unsupported browser is requested
			throw new IllegalArgumentException("Browser not supported: " + browserName);
		}

					// Set timeouts from configuration file
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait())); // Wait for elements to appear
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout())); // Wait for page to load completely

		// Initialize WebDriverWait for explicit waits throughout the framework
		wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));

			logger.info("WebDriver initialized successfully");

		} catch (Exception e) {
			logger.error("Failed to initialize WebDriver: {}", e.getMessage());
			throw new RuntimeException("Driver initialization failed", e);
		}
	}

	/**
	 * Navigate to application URL
	 */
	public void navigateToApplication() {
		try {
			// Get application URL from configuration properties file
			String url = ConfigReader.getProperty("app.url");
			logger.info("Navigating to application URL: {}", url);
			
			// Navigate to the application URL using WebDriver
			driver.get(url);

			logger.info("Successfully navigated to application");
		} catch (Exception e) {
			logger.error("Failed to navigate to application: {}", e.getMessage());
			throw new RuntimeException("Navigation failed", e);
		}
	}

	/**
	 * Close browser and cleanup resources
	 */
	public void tearDown() {
		try {
			// Check if driver instance exists before attempting to close
			if (driver != null) {
				logger.info("Closing browser and cleaning up resources");
				driver.quit(); // Close all browser windows and end WebDriver session
				logger.info("Browser closed successfully");
			}
		} catch (Exception e) {
			// Log any errors during cleanup but don't throw exceptions
			logger.error("Error during teardown: {}", e.getMessage());
		}
	}

	/**
	 * Get WebDriver instance
	 * 
	 * @return WebDriver instance
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * Get WebDriverWait instance
	 * 
	 * @return WebDriverWait instance
	 */
	public WebDriverWait getWait() {
		return wait;
	}
}
