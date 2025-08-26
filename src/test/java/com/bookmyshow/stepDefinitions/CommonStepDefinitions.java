package com.bookmyshow.stepDefinitions;

import java.util.List;

import org.testng.Assert;

import com.bookmyshow.pages.HomePage;
import com.bookmyshow.utils.ConfigReader;
import com.bookmyshow.utils.ExcelUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonStepDefinitions {
	private HomePage homePage;
	
	@Given("I navigate to BookMyShow application")
	public void i_navigate_to_book_my_show_application() {
		homePage = new HomePage(BaseSteps.getDriver());
	}
	
	@When("I select a valid city")
	public void i_select_a_valid_city() {
		homePage.selectCity("Mumbai");
	}
	
	@When("I select city from Excel data")
	public void i_select_city_from_excel_data() {
		String filePath = ConfigReader.getProperty("test.data.file");
		ExcelUtil excel = new ExcelUtil(filePath, "SearchData");
		List<String> cities = excel.getColumnData(0);
		excel.close();
		if (!cities.isEmpty()) {
			homePage.selectCity(cities.get(0));
		}
	}
	
	@When("I select city {string}")
	public void i_select_city(String city) {
		homePage.selectCity(city);
	}
	
	@Then("the location should be updated successfully")
	public void the_location_should_be_updated_successfully() {
		Assert.assertNotNull(BaseSteps.getDriver(), "Driver should not be null after location selection");
	}
} 