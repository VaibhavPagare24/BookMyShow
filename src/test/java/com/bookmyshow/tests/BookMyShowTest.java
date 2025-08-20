package com.bookmyshow.tests;

import com.bookmyshow.base.DriverSetup;
import com.bookmyshow.pages.EventsPage;
import com.bookmyshow.pages.HomePage;
import com.bookmyshow.pages.MoviesPage;
import com.bookmyshow.pages.SportsPage;
import com.bookmyshow.utils.BrowserHelpers;
import com.bookmyshow.utils.ConfigReader;
import com.bookmyshow.utils.ExcelUtil;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookMyShowTest extends DriverSetup {

	private HomePage home;
	private MoviesPage movies;
	private SportsPage sports;
	private EventsPage events;
	// Utility instances
	private BrowserHelpers helper; // Browser window/tab management
	private String excelFile; // Path to test data Excel file
	private ExcelUtil excelUtil; // Excel file operations utility

	// Logger instance for test execution tracking
	private static final Logger logger = LogManager.getLogger(BookMyShowTest.class);

	@BeforeClass
	public void setUp() {
		logger.info("Test setup started");

		// Initialize WebDriver based on browser configuration
		initializeDriver(ConfigReader.getProperty("browser"));
		// Navigate to Flipkart application
		navigateToApplication();

		home = new HomePage(driver);
		movies = new MoviesPage(driver);
		sports = new SportsPage(driver);
		events = new EventsPage(driver);
		// Initialize utility instances
		helper = new BrowserHelpers(driver);
		excelFile = ConfigReader.getProperty("test.data.file"); // Get Excel file path from config

		logger.info("Test setup completed");
	}

	@Test(priority = 1)
	public void selectLocation() {
		// Read search keywords from Excel file
		excelFile = ConfigReader.getProperty("test.data.file");
		excelUtil = new ExcelUtil(excelFile, "SearchData"); // Open SearchData sheet

		List<String> searchKeywords = excelUtil.getColumnData(0); // Extract keywords from first column
		excelUtil.close(); // Close Excel file after reading

		// Use first keyword from Excel data for search
		// Note: Could be enhanced to loop through multiple keywords
		String keyword = searchKeywords.get(0); // Get first search keyword
		logger.info("Search keyword from Excel: " + keyword);
		home.selectCity(keyword);
		logger.info("Location selection test completed successfully");
	}

	@Test(priority = 2)
	public void getSportEvents() {
		sports.clickSportsTab();
		sports.extractDetails();
		logger.info("Sports events test completed successfully");
	}

	@Test(priority = 3)
	public void movieLanguages() {
		logger.info("Movie languages");
		movies.clickMoviesTab();
		movies.getLanguages();
		logger.info("Movie languages test completed successfully");
	}

	
	@Test(priority = 4)
	public void getEvents() {
		events.clickEventTab();
		events.selectWeekend();
		events.selectPriceRance();
		events.extractDetails();
		logger.info("Events test completed successfully");
	}

	
	
	@AfterClass
	public void tearDown() {
		logger.info("Test suite completed - performing final cleanup");

		if (driver != null) {
			driver.quit();
			logger.info("WebDriver closed successfully");
		}

		logger.info("Test execution completed");
	}
}
